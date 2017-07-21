package sber.tech.habr.parsers;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import sber.tech.habr.managers.HabrItemsManager;
import sber.tech.habr.models.HabrItem;

public class HabrParser {

    public static void parseRSS(XmlPullParser mParser)  {
        String LOG_TAG = "habr_debug_tag";

        Boolean captureItem = false;
        Boolean captureTitle = false;
        Boolean captureDescription = false;
        Boolean captureLink = false;
        Integer counter = 0;

        HabrItem habrNews = null;

        try {
            while (mParser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (mParser.getEventType()) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:

                        if (mParser.getName().equals("item")) {
                            captureItem = true;
                            habrNews = new HabrItem();
                        };

                        captureTitle = (mParser.getName().equals("title") && captureItem) ? true : false;
                        captureDescription = (mParser.getName().equals("description") && captureItem) ? true : false;
                        captureLink = (mParser.getName().equals("guid") && captureItem) ? true : false;

                        break;
                    case XmlPullParser.END_TAG:
                        if (mParser.getName().equals("item")) {
                            if (captureItem) {
                                captureItem = false;
                                HabrItemsManager.ITEMS.add(habrNews);
                                HabrItemsManager.ITEM_MAP.put(habrNews.getLink(), habrNews);
                                counter++;
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:

                        if (captureTitle && habrNews.getTitle()==null ) {
                            habrNews.setTitle(mParser.getText());
                        }

                        if (captureDescription  && habrNews.getDescription()==null ) {
                            habrNews.setDescription(mParser.getText());
                        }

                        if (captureLink && habrNews.getLink()==null ) {
                            habrNews.setLink(mParser.getText());
                        }

                        break;

                    default:
                        break;
                }

                mParser.next();
            }
        } catch (XmlPullParserException e) {
            Log.d(LOG_TAG,"Error:" + e.toString());
        } catch (IOException e) {
            Log.d(LOG_TAG,"Error:" + e.toString());
        }

    }

}
