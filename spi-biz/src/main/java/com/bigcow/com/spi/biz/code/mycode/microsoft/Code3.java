package com.bigcow.com.spi.biz.code.mycode.microsoft;

public class Code3 {

    public static void main(String[] args) {
        // 字典 存在与否， abc,    ab.
        String[] words = { "abc", "efg", "ade" };
        Trie trie = new Trie();
        for (int i = 0; i < words.length; i++) {
            trie.addWord(words[i]);
        }

//        System.out.println(trie.wordMatch(trie, "abc")); // true
//        System.out.println(trie.wordMatch(trie, "abcd")); // false
//        System.out.println(trie.wordMatch(trie, "a.c")); // true
//        System.out.println(trie.wordMatch(trie, "ab.")); // true
//        System.out.println(trie.wordMatch(trie, "ab.d")); // false
//        System.out.println(trie.wordMatch(trie, "a..")); // true
        System.out.println(trie.wordMatch(trie, "a.")); // false
        System.out.println(trie.wordMatch(trie, "m..")); // false
    }

}

class Trie {

    private Trie[] children;
    private boolean end;

    public Trie() {
        this.children = new Trie[26];
        this.end = false;
    }

    public boolean wordMatch(Trie root, String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if (c != '.' && root.children[index] == null) {
                return false;
            }
            if (c == '.') {
                for (int j = 0; j < 26; j++) {
                    if (root.children[j] != null) {
                        if (wordMatch(root.children[j], word.substring(i + 1))) {
                            return true;
                        }
                    }
                }
            } else {
                root = root.children[index];
            }
        }
        return root.end == true;
    }

    public void addWord(String word) {
        Trie root = this;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if (root.children[index] == null) {
                root.children[index] = new Trie();
            }
            root = root.children[index];
        }
        root.end = true;
    }
}
