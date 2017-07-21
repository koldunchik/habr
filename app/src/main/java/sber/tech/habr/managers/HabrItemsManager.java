package sber.tech.habr.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sber.tech.habr.models.HabrItem;

public class HabrItemsManager {

    public static final ArrayList<HabrItem> ITEMS = new ArrayList<HabrItem>();

    public static final Map<String, HabrItem> ITEM_MAP = new HashMap<String, HabrItem>();

    private static void addItem(HabrItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getLink(), item);
    }
}
