package luca.com.moviestartplanet.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.onesignal.OneSignal;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luca.com.moviestartplanet.MovieStarApplication;
import luca.com.moviestartplanet.R;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.home_layout)
    LinearLayout homeLayout;
    @Bind(R.id.share_layout)
    LinearLayout shareLayout;
    @Bind(R.id.favorite_layout)
    LinearLayout rateLayout;
    @Bind(R.id.exit_layout)
    LinearLayout exitLayout;

    @Bind(R.id.iv_home)
    ImageView ivHome;
    @Bind(R.id.iv_share)
    ImageView ivShare;
    @Bind(R.id.iv_favorite)
    ImageView ivFavorite;
    @Bind(R.id.iv_exit)
    ImageView ivExit;

    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.tv_share)
    TextView tvShare;
    @Bind(R.id.tv_favorite)
    TextView tvFavorite;
    @Bind(R.id.tv_exit)
    TextView tvExit;

    @Bind(R.id.first_layout)
    LinearLayout firstLayout;
    @Bind(R.id.second_layout)
    ScrollView secondLayout;
    @Bind(R.id.third_layout)
    LinearLayout thirdLayout;
    @Bind(R.id.fourth_layout)
    LinearLayout fourthLayout;
    @Bind(R.id.fifth_layout)
    LinearLayout fifthLayout;
    @Bind(R.id.last_layout)
    LinearLayout lastLayout;
    @Bind(R.id.no_internet_layout)
    LinearLayout noInternetLayout;

    @Bind(R.id.progress_1)
    RoundCornerProgressBar progress1;
    @Bind(R.id.progress_2)
    RoundCornerProgressBar progres2;
    @Bind(R.id.progress_3)
    RoundCornerProgressBar progress3;
    @Bind(R.id.progress_4)
    RoundCornerProgressBar progres4;
    @Bind(R.id.progress_5)
    RoundCornerProgressBar progress5;

    @Bind(R.id.loading_1)
    SpinKitView loading1;
    @Bind(R.id.loading_2)
    SpinKitView loading2;
    @Bind(R.id.loading_3)
    SpinKitView loading3;
    @Bind(R.id.loading_4)
    SpinKitView loading4;
    @Bind(R.id.loading_5)
    SpinKitView loading5;

    @Bind(R.id.ic_check1)
    ImageView ivCheck1;
    @Bind(R.id.ic_check2)
    ImageView ivCheck2;
    @Bind(R.id.ic_check3)
    ImageView ivCheck3;
    @Bind(R.id.ic_check4)
    ImageView ivCheck4;
    @Bind(R.id.ic_check5)
    ImageView ivCheck5;

    @Bind(R.id.tv_progress_1)
    TextView tvProgress1;
    @Bind(R.id.tv_progress_2)
    TextView tvProgress2;
    @Bind(R.id.tv_progress_3)
    TextView tvProgress3;
    @Bind(R.id.tv_progress_4)
    TextView tvProgress4;
    @Bind(R.id.tv_progress_5)
    TextView tvProgress5;

    @Bind(R.id.btn_continue)
    Button btnContinue;
    @Bind(R.id.btn_check)
    Button btnCheck;
    @Bind(R.id.btn_check_app_version)
    Button btnCheckApp;
    @Bind(R.id.btn_continue_last)
    Button btnContinueLast;

    @Bind(R.id.spin_coin)
    EditText spinCoin;
    @Bind(R.id.spin_gem)
    EditText spinGem;

    private AdView admobBanner;

    private InterstitialAd adInterstitial;
    private Handler mHandler;
    private Runnable displayAd;

    private static int nFirst = 0;
    private static int nSecond = 0;
    private static int nThird = 0;
    private static int nFourth = 0;
    private static int nFifth = 0;

    private static final String[] items = {"25,000", "50,000", "120,000", "187,500", "332,500", "800,000", "9,999,999"};

    private static int coinPosition = 0;
    private static int gemPosition = 0;

    private Handler firstHandler = new Handler();
    private Runnable firstRunnable = new Runnable() {
        @Override
        public void run() {
            tvProgress1.setText(nFirst + "%");
            progress1.setProgress(nFirst);
            if(nFirst >= 100) {
                secondLayout.setVisibility(View.GONE);
                thirdLayout.setVisibility(View.VISIBLE);
                loading1.setVisibility(View.GONE);
                ivCheck1.setVisibility(View.VISIBLE);

                firstHandler.removeMessages(0);
                firstHandler.removeCallbacks(firstRunnable);

                btnCheck.setEnabled(true);
                nSecond = 0;
            } else {
                nFirst++;

                if (nFirst % 40 == 0) {
                    showInterstitial();
                }

                firstHandler.postDelayed(this, 500);
            }
        }
    };

    private Handler secondHandler = new Handler();
    private Runnable secondRunnable = new Runnable() {
        @Override
        public void run() {
            tvProgress2.setText(nSecond + "%");
            progres2.setProgress(nSecond);
            if(nSecond >= 100) {
                thirdLayout.setVisibility(View.GONE);
                fourthLayout.setVisibility(View.VISIBLE);
                loading2.setVisibility(View.GONE);
                ivCheck2.setVisibility(View.VISIBLE);

                secondHandler.removeCallbacks(secondRunnable);
                btnCheckApp.setEnabled(true);
                nThird = 0;
            }else {
                nSecond++;
                if (nSecond % 40 == 0) {
                    showInterstitial();
                }

                secondHandler.postDelayed(this, 500);
            }
        }
    };

    private Handler thirdHandler = new Handler();
    private Runnable thirdRunnable = new Runnable() {
        @Override
        public void run() {
            tvProgress3.setText(nThird + "%");
            progress3.setProgress(nThird);
            if(nThird >= 100) {
                fourthLayout.setVisibility(View.GONE);
                fifthLayout.setVisibility(View.VISIBLE);
                loading3.setVisibility(View.GONE);
                ivCheck3.setVisibility(View.VISIBLE);

                thirdHandler.removeCallbacks(thirdRunnable);

                btnContinueLast.setEnabled(true);
                nFourth = 0;
            } else {
                nThird++;

                if (nThird % 40 == 0) {
                    showInterstitial();
                }

                thirdHandler.postDelayed(this, 500);
            }
        }
    };

    private Handler fourthHandler = new Handler();
    private Runnable fourthRunnable = new Runnable() {
        @Override
        public void run() {
            tvProgress4.setText(nFourth + "%");
            progres4.setProgress(nFourth);
            if(nFourth >= 100) {
                fifthLayout.setVisibility(View.GONE);
                lastLayout.setVisibility(View.VISIBLE);
                loading4.setVisibility(View.GONE);
                ivCheck4.setVisibility(View.VISIBLE);

                fourthHandler.removeCallbacks(fourthRunnable);
            } else {
                nFourth++;

                if (nFourth % 40 == 0) {
                    showInterstitial();
                }

                fourthHandler.postDelayed(this, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        adInterstitial = new InterstitialAd(MainActivity.this);
        adInterstitial.setAdUnitId("ca-app-pub-1588855366653236/4173011907");
        adInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadAd();
            }
        });

        mHandler = new Handler(Looper.getMainLooper());
        displayAd = new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (adInterstitial.isLoaded()) {
                            adInterstitial.show();
                        }
                    }
                });
            }
        };

        homeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ivHome.setBackground(getResources().getDrawable(R.mipmap.ic_home_filled));
                    tvHome.setTextColor(getResources().getColor(R.color.colorRed));

                    ivShare.setBackground(getResources().getDrawable(R.mipmap.ic_share));
                    tvShare.setTextColor(getResources().getColor(R.color.colorWhite));
                    ivFavorite.setBackground(getResources().getDrawable(R.mipmap.ic_favorite));
                    tvFavorite.setTextColor(getResources().getColor(R.color.colorWhite));
                    ivExit.setBackground(getResources().getDrawable(R.mipmap.ic_exit));
                    tvExit.setTextColor(getResources().getColor(R.color.colorWhite));
                } else if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                }
                return false;
            }
        });

        shareLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ivShare.setBackground(getResources().getDrawable(R.mipmap.ic_share_filled));
                    tvShare.setTextColor(getResources().getColor(R.color.colorRed));

                    ivHome.setBackground(getResources().getDrawable(R.mipmap.ic_home));
                    tvHome.setTextColor(getResources().getColor(R.color.colorWhite));
                    ivFavorite.setBackground(getResources().getDrawable(R.mipmap.ic_favorite));
                    tvFavorite.setTextColor(getResources().getColor(R.color.colorWhite));
                    ivExit.setBackground(getResources().getDrawable(R.mipmap.ic_exit));
                    tvExit.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                return false;
            }
        });

        rateLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ivFavorite.setBackground(getResources().getDrawable(R.mipmap.ic_favorite_filled));
                    tvFavorite.setTextColor(getResources().getColor(R.color.colorRed));

                    ivHome.setBackground(getResources().getDrawable(R.mipmap.ic_home));
                    tvHome.setTextColor(getResources().getColor(R.color.colorWhite));
                    ivShare.setBackground(getResources().getDrawable(R.mipmap.ic_share));
                    tvShare.setTextColor(getResources().getColor(R.color.colorWhite));
                    ivExit.setBackground(getResources().getDrawable(R.mipmap.ic_exit));
                    tvExit.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                return false;
            }
        });

        exitLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ivExit.setBackground(getResources().getDrawable(R.mipmap.ic_exit_filled));
                    tvExit.setTextColor(getResources().getColor(R.color.colorRed));

                    ivHome.setBackground(getResources().getDrawable(R.mipmap.ic_home));
                    tvHome.setTextColor(getResources().getColor(R.color.colorWhite));
                    ivShare.setBackground(getResources().getDrawable(R.mipmap.ic_share));
                    tvShare.setTextColor(getResources().getColor(R.color.colorWhite));
                    ivFavorite.setBackground(getResources().getDrawable(R.mipmap.ic_favorite));
                    tvFavorite.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                return false;
            }
        });

        checkInternetConnection();
    }

    private void checkInternetConnection() {
        if(isNetworkAvailable(MainActivity.this)) {
            firstLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);

            admobBanner = new AdView(MainActivity.this);
            admobBanner.setAdUnitId(MovieStarApplication.ADMOB_BANNER_UNIT_ID);
            admobBanner.setAdSize(AdSize.SMART_BANNER);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    //.addTestDevice("A39667C0609A678E4C2174E9E7F5432A")
                    .build();

            if(admobBanner.getAdSize() != null || admobBanner.getAdUnitId() != null)
                admobBanner.loadAd(adRequest);
            // else Log state of adsize/adunit
            ((LinearLayout)findViewById(R.id.admob_view)).addView(admobBanner);

            loadAd();
        } else {
            firstLayout.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void loadAd() {
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Load the adView object witht he request
        adInterstitial.loadAd(adRequest);
    }

    private void showInterstitial() {
        mHandler.postDelayed(displayAd, 1000);
    }

    /*@OnClick(R.id.spin_coin)
    void onClickSpinCoin() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(items, coinPosition, null)
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        coinPosition = selectedPosition;
                        spinCoin.setText(items[selectedPosition]);
                    }
                })
                .show();
    }

    @OnClick(R.id.spin_gem)
    void onClickSpinGem() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(items, gemPosition, null)
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        gemPosition = selectedPosition;
                        spinGem.setText(items[selectedPosition]);
                    }
                })
                .show();
    }*/

    @OnClick(R.id.btn_try_again)
    void onClickTryAgain() {
        checkInternetConnection();
    }

    @OnClick(R.id.home_layout)
    void onClickHome() {
        secondLayout.setVisibility(View.GONE);
        thirdLayout.setVisibility(View.GONE);
        fourthLayout.setVisibility(View.GONE);
        fifthLayout.setVisibility(View.GONE);
        lastLayout.setVisibility(View.GONE);
        firstLayout.setVisibility(View.VISIBLE);

        firstHandler.removeCallbacks(firstRunnable);
        secondHandler.removeCallbacks(secondRunnable);
        thirdHandler.removeCallbacks(thirdRunnable);
        fourthHandler.removeCallbacks(fourthRunnable);
    }

    @OnClick(R.id.btn_connect)
    void onClickConnect() {
        firstLayout.setVisibility(View.GONE);
        secondLayout.setVisibility(View.VISIBLE);
        btnContinue.setEnabled(true);
        nFirst = 0;

        showInterstitial();
    }

    @OnClick(R.id.btn_continue)
    void onClickContinue() {
        firstHandler.post(firstRunnable);
        loading1.setVisibility(View.VISIBLE);
        ivCheck1.setVisibility(View.GONE);

        btnContinue.setEnabled(false);

        showInterstitial();
    }

    @OnClick(R.id.btn_check)
    void onClickCheck() {
        secondHandler.post(secondRunnable);
        loading2.setVisibility(View.VISIBLE);
        ivCheck2.setVisibility(View.GONE);
        btnCheck.setEnabled(false);

        showInterstitial();
    }

    @OnClick(R.id.btn_check_app_version)
    void onClickCheckApp() {
        thirdHandler.post(thirdRunnable);
        loading3.setVisibility(View.VISIBLE);
        ivCheck3.setVisibility(View.GONE);
        btnCheckApp.setEnabled(false);

        showInterstitial();
    }

    @OnClick(R.id.btn_continue_last)
    void onClickContinueLast() {
        fourthHandler.post(fourthRunnable);
        loading4.setVisibility(View.VISIBLE);
        ivCheck4.setVisibility(View.GONE);
        btnContinueLast.setEnabled(false);

        showInterstitial();
    }

    @OnClick(R.id.btn_rate)
    void onClickRateApp() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=hungarandroapplis.cheatsjokeandprank.funappformvistrplntprank")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=hungarandroapplis.cheatsjokeandprank.funappformvistrplntprank")));
        }
    }

    @OnClick(R.id.share_layout)
    void onClickShare() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, R.string.share_content);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @OnClick(R.id.favorite_layout)
    void onClickRate() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=hungarandroapplis.cheatsjokeandprank.funappformvistrplntprank")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=hungarandroapplis.cheatsjokeandprank.funappformvistrplntprank")));
        }
    }

    @OnClick(R.id.exit_layout)
    void onClickExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.confirm));
        builder.setMessage(getString(R.string.sure_to_quit));
        builder.setPositiveButton(getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
}
