package test;


import java.util.Objects;

import java.util.Random;


public class Tile {
    private static final int Amount = 26;
    final int score;
    final char letter;

    private Tile(int score, char letter) {
        super();
        this.score = score;
        this.letter = letter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        return letter == other.letter && score == other.score;
    }

    public static class Bag {
        int[] letterAmount = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        Tile[] tiles = new Tile[Amount];
        ;
        private final int[] maxforEach = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        private static Bag bag;

        private Bag() {
            super();
            int j = 0;
            for (char i = 'A'; i <= 'Z'; i++) {
                switch (i) {
                    case 'A', 'E', 'I', 'L', 'N', 'O', 'R', 'U', 'T', 'S' -> {
                        this.tiles[j] = new Tile(1, i);
                    }
                    case 'B', 'C', 'M', 'P' -> {
                        this.tiles[j] = new Tile(3, i);
                    }
                    case 'D', 'G' -> {
                        this.tiles[j] = new Tile(2, i);
                    }
                    case 'F', 'H', 'Y', 'W', 'V' -> {
                        this.tiles[j] = new Tile(4, i);
                    }
                    case 'J', 'X' -> {
                        this.tiles[j] = new Tile(8, i);
                    }
                    case 'K' -> {
                        this.tiles[j] = new Tile(5, i);
                    }
                    case 'Q', 'Z' -> {
                        this.tiles[j] = new Tile(10, i);
                    }
                    default -> {
                    }
                }
                j++;
            }
        }

        static Bag getBag() {
            if (bag != null)
                return bag;
            bag = new Bag();
            return bag;
        }

        Tile getRand() {
            if (this.isEmpty())
                return null;
            Random rand = new Random();
            int i = rand.nextInt(Amount - 1);
            while (this.letterAmount[i] == 0)
                i = rand.nextInt(Amount - 1);
            letterAmount[i]--;
            return tiles[i];
        }

        Tile getTile(char letter) {
            if (this.isEmpty())
                return null;
            int j = 0;
            for (char i = 'A'; i <= 'Z'; i++) {
                if (i == letter)
                    break;
                j++;
            }
            if (j == 26)
                return null;
            if (letterAmount[j] == 0)
                return null;
            return tiles[j];
        }

        boolean isEmpty() {
            int empty = 0;
            for (int j = 0; j < Amount; j++)
                if (letterAmount[j] != 0)
                    return false;
                else
                    empty++;
            return empty == Amount;
        }

        void put(Tile t) {
            int i = 0;
            for (Tile T : tiles) {
                if (T.equals(t)) {
                    if (letterAmount[i] != maxforEach[i]) {
                        letterAmount[i]++;
                        break;
                    }
                }
                i++;
            }
        }

        int[] getQuantities() {
            return letterAmount.clone();
        }

        /*int size() {
            int sum = 0;
            if (this.isEmpty())
                return 0;
            for (int i = 0; i < Amount; i++) {
                sum += letterAmount[i];
            }
            return sum;
        }*/
    }

}
