// MIT License
// Copyright (c) 2017 SNU_ARToon Team

package org.tensorflow.demo;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Size;
import android.util.TypedValue;
import android.view.Display;
import android.widget.ImageView;
import java.util.List;
import org.tensorflow.demo.env.BorderedText;
import org.tensorflow.demo.env.ImageUtils;
import org.tensorflow.demo.env.Logger;
import com.snu_artoon.arwebtoonplayer.R;

public class ARToonActivity extends CameraActivity implements OnImageAvailableListener {
    private static final Logger LOGGER = new Logger();

    private static final String YOLO_MODEL_FILE_FACE = "file:///android_asset/tiny-yolo-1c.pb";
    private static final String YOLO_MODEL_FILE_HAND = "file:///android_asset/tiny-yolo-hand.pb";

    private static final int YOLO_INPUT_SIZE = 416;
    private static final String YOLO_INPUT_NAME = "input";
    private static final String YOLO_OUTPUT_NAMES = "output";
    private static final int YOLO_BLOCK_SIZE = 32;

    private static final boolean SAVE_PREVIEW_BITMAP = false;

    private static final boolean MAINTAIN_ASPECT = true;

    private static final Size DESIRED_PREVIEW_SIZE = new Size(640, 480);

    private Classifier classifier;

    private Integer sensorOrientation;

    private int previewWidth = 0;
    private int previewHeight = 0;
    private byte[][] yuvBytes;
    private int[] rgbBytes = null;
    private Bitmap rgbFrameBitmap = null;
    private Bitmap croppedBitmap = null;

    private Bitmap cropCopyBitmap;

    private boolean computing = false;

    private Matrix frameToCropTransform;
    private Matrix cropToFrameTransform;

    private BorderedText borderedText;

    private long startTime = 0;
    private long endTime = 1000;

    @Override
    protected int getLayoutId() {
        return R.layout.camera_connection_fragment;
    }

    @Override
    protected Size getDesiredPreviewFrameSize() {
        return DESIRED_PREVIEW_SIZE;
    }

    private static final float TEXT_SIZE_DIP = 10;

    @Override
    public void onPreviewSizeChosen(final Size size, final int rotation) {
        final float textSizePx =
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DIP, getResources().getDisplayMetrics());
        borderedText = new BorderedText(textSizePx);
        borderedText.setTypeface(Typeface.MONOSPACE);

        if (TensorFlowYoloDetector.selectedModel == 0) {
            classifier = TensorFlowYoloDetector.create(
                    getAssets(),
                    YOLO_MODEL_FILE_FACE,
                    YOLO_INPUT_SIZE,
                    YOLO_INPUT_NAME,
                    YOLO_OUTPUT_NAMES,
                    YOLO_BLOCK_SIZE);

        } else {
            classifier = TensorFlowYoloDetector.create(
                    getAssets(),
                    YOLO_MODEL_FILE_HAND,
                    YOLO_INPUT_SIZE,
                    YOLO_INPUT_NAME,
                    YOLO_OUTPUT_NAMES,
                    YOLO_BLOCK_SIZE);
        }


        previewWidth = size.getWidth();
        previewHeight = size.getHeight();

        final Display display = getWindowManager().getDefaultDisplay();
        final int screenOrientation = display.getRotation();

        LOGGER.i("Sensor orientation: %d, Screen orientation: %d", rotation, screenOrientation);

        sensorOrientation = rotation + screenOrientation;

        LOGGER.i("Initializing at size %dx%d", previewWidth, previewHeight);
        rgbBytes = new int[previewWidth * previewHeight];
        rgbFrameBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Config.ARGB_8888);
        croppedBitmap = Bitmap.createBitmap(YOLO_INPUT_SIZE, YOLO_INPUT_SIZE, Config.ARGB_8888);

        frameToCropTransform =
                ImageUtils.getTransformationMatrix(
                        previewWidth, previewHeight,
                        YOLO_INPUT_SIZE, YOLO_INPUT_SIZE,
                        sensorOrientation, MAINTAIN_ASPECT);

        cropToFrameTransform = new Matrix();
        frameToCropTransform.invert(cropToFrameTransform);

        yuvBytes = new byte[3][];

    }

    @Override
    public void onImageAvailable(final ImageReader reader) {
        Image image = null;

        try {
            image = reader.acquireLatestImage();

            if (image == null) {
                return;
            }

            if (computing) {
                image.close();
                return;
            }
            computing = true;

            Trace.beginSection("imageAvailable");

            final Plane[] planes = image.getPlanes();
            fillBytes(planes, yuvBytes);

            final int yRowStride = planes[0].getRowStride();
            final int uvRowStride = planes[1].getRowStride();
            final int uvPixelStride = planes[1].getPixelStride();
            ImageUtils.convertYUV420ToARGB8888(
                    yuvBytes[0],
                    yuvBytes[1],
                    yuvBytes[2],
                    previewWidth,
                    previewHeight,
                    yRowStride,
                    uvRowStride,
                    uvPixelStride,
                    rgbBytes);

            image.close();
        } catch (final Exception e) {
            if (image != null) {
                image.close();
            }
            LOGGER.e(e, "Exception!");
            Trace.endSection();
            return;
        }

        rgbFrameBitmap.setPixels(rgbBytes, 0, previewWidth, 0, 0, previewWidth, previewHeight);
        final Canvas canvas = new Canvas(croppedBitmap);
        canvas.drawBitmap(rgbFrameBitmap, frameToCropTransform, null);

        // For examining the actual TF input.
        if (SAVE_PREVIEW_BITMAP) {
            ImageUtils.saveBitmap(croppedBitmap);
        }

        runInBackground(
                new Runnable() {
                    @Override
                    public void run() {
                        if (endTime - startTime > 100) {
                            startTime = SystemClock.uptimeMillis();
                            final List<Classifier.Recognition> results = classifier.recognizeImage(croppedBitmap);

                            ImageView imageView = (ImageView) findViewById(R.id.debug_overlay);
                            cropCopyBitmap = Bitmap.createBitmap(imageView.getWidth() / 3,
                                    imageView.getHeight() / 3, Config.ARGB_4444);

                            final Canvas canvas = new Canvas(cropCopyBitmap);

                            Bitmap image;
                            if (TensorFlowYoloDetector.selectedModel == 0) {
                                image = BitmapFactory.decodeResource(getResources(), R.drawable.girl_frame_00001);
                            } else {
                                image = BitmapFactory.decodeResource(getResources(), R.drawable.thumb);
                            }

                            for (final Classifier.Recognition res : results) {
                                final RectF location = res.getLocation();

                                if (location == null || res.getConfidence() < 0.3f) {
                                    continue;
                                }

                                if (TensorFlowYoloDetector.selectedModel == 0) {
                                    float originalRatio = 2.1f;

                                    location.left *= 0.9;
                                    location.right *= 1.1;

                                    float width = location.width();
                                    location.left += width * 2 / 3;
                                    location.right += width * 2 / 3;

                                    location.top *= 1.1;
                                    location.bottom = location.top + originalRatio * location.width();
                                } else {
                                    location.top *= 0.9;
                                    location.bottom *= 1.1;
                                    location.left *= 0.9;
                                    location.right *= 1.1;
                                }

                                canvas.drawBitmap(image, null, location, new Paint());
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ImageView imageView = (ImageView) findViewById(R.id.debug_overlay);
                                    imageView.setImageDrawable(new BitmapDrawable(getResources(), cropCopyBitmap));
                                }
                            });

                            Trace.endSection();
                            computing = false;
                            endTime = SystemClock.uptimeMillis();
                        }
                    }
                });
    }
}
