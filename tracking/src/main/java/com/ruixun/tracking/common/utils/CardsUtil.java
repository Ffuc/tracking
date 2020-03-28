package com.ruixun.tracking.common.utils;


import com.ruixun.tracking.entity.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Program: tracking_system
 * <p>
 * Description:
 *
 * @Date: 2020-03-25 16:20
 **/
public class CardsUtil {
    public static boolean checkCards(int type, List<Card> list) {
        return true;
    }

    public static String parseString(List<Card> list) {
        String cards = "";
        for (int i = 0; i < list.size(); i++) {
            Card card = list.get(i);
            cards += card.getColor() + "-" + card.getSize() + ",";
        }
        return cards;
    }

    public static List<Card> parseCards(String cards) {
        List<Card> list = new ArrayList<>();
        String[] arr = cards.split(",");
        for (int i = 0; i < list.size(); i++) {
            String[] arr2 = arr[i].split("-");
            Card card = new Card();
            card.setColor(Integer.parseInt(arr2[0]));
            card.setSize(Integer.parseInt(arr2[1]));
            list.add(card);
        }
        return list;
    }
}
