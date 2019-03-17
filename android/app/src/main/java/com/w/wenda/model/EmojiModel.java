package com.w.wenda.model;

import java.util.List;

public class EmojiModel {
    public List<EmojiItemModel> emoji_list;

    public List<EmojiItemModel> getEmoji_list() {
        return emoji_list;
    }

    public void setEmoji_list(List<EmojiItemModel> emoji_list) {
        this.emoji_list = emoji_list;
    }

    @Override
    public String toString() {
        return "EmojiModel{" +
                "emoji_list=" + emoji_list +
                '}';
    }

    public class EmojiItemModel{
        private String name;
        private int unicode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUnicode() {
            return unicode;
        }

        public void setUnicode(int unicode) {
            this.unicode = unicode;
        }

        @Override
        public String toString() {
            return "EmojiItemModel{" +
                    "name='" + name + '\'' +
                    ", unicode='" + unicode + '\'' +
                    '}';
        }
    }

}
