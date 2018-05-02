package com.example.christianwilliams.cocktails;

public class Cocktail {
    String ID;
    String Name;
    String Thumb;


        public String getName() {
            return Name;
        }

        public void setName(String name) {
            this.Name = name;
        }

        public String getID() {
        return ID;
    }

        public void setID(String id) {
        this.ID = id;
    }

        public String getThumb() {
            return Thumb;
        }

        public void setThumb(String thumb) {
            this.Thumb = thumb;
        }

    public Cocktail() {
        this.Name = "";
        this.ID = "";
        this.Thumb ="";
    }

        public Cocktail(Cocktail cocktail) {
            this.Name =cocktail.Name;
            this.ID = cocktail.ID;
            this.Thumb =cocktail.Thumb;
        }
}
