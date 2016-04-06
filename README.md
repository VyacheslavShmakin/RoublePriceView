# GooglePlay-CollapsingToolbar
Simple PriceView that can be used to represent any prices in Russian rouble currency.
Library uses RobotoTextView as text presenter.

Download
--------

Gradle:

```groovy
compile 'com.github.VyacheslavShmakin.rouble-price-view:1.0.1'
```

Maven:

```xml
<dependency>
    <groupId>com.github.VyacheslavShmakin</groupId>
    <artifactId>rouble-price-view</artifactId>
    <version>1.0.1</version>
    <type>aar</type>
</dependency>
```


Usage
-----
#### In Code
If you wanna configure PriceView programmatically you should use these methods:
``` java
mYourPriceView.setTextSize(<text size value>);
mYourPriceView.setRegionLength(<region length limiter>);
mYourPriceView.setRoublePadding(<padding value between price and rouble sign>);
mYourPriceView.setTypeface(<set your own typeface if you need>);
mYourPriceView.setTextColor(<just text color>);
mYourPriceView.setValue(<price value>);
```

-----
#### In xml

```xml
 <ru.shmakinv.android.widget.PriceView
        android:id="@+id/yourPriceView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        app:textColor="@color/textColorPrimary"
        app:textSize="14sp"
        app:roublePadding="4dp"
        app:typeface="roboto_regular"
        app:regionLength="6"/>
```