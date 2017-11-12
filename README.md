# Rouble PriceView
Simple PriceView can be used to represent any prices in Russian rouble currency.

Download
--------

Gradle:

```groovy
compile 'com.github.VyacheslavShmakin.rouble-price-view:1.0.3'
```

Maven:

```xml
<dependency>
    <groupId>com.github.VyacheslavShmakin</groupId>
    <artifactId>rouble-price-view</artifactId>
    <version>1.0.3</version>
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
        android:textColor="@color/textColorPrimary"
        android:textSize="14sp"
        android:fontFamily="@font/roboto_regular"
        app:pv_price_gravity="center"
        app:pv_roublePadding="4dp"
        app:pv_regionLength="6"
        app:pv_value="100500"/>
```