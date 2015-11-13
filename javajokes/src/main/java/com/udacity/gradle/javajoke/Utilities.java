package com.udacity.gradle.javajoke;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Elias Myronidis on 13/11/15.
 */
public class Utilities {

    public static String getJokes(){
        Random random = new Random();
        int max = 9;
        int min = 0;

        int jokeNumber = random.nextInt(max - min) + min;
        return jokes(jokeNumber);
    }

    private static String jokes(int jokeNum){
        HashMap<Integer, String> jokesMap = new HashMap<>();
        jokesMap.put(0, "When I see lovers' names carved in a tree, I don't think it's sweet. I just think it's surprising how many people bring a knife on a date.");
        jokesMap.put(1, "Can a kangaroo jump higher than a house? Of course, a house doesn’t jump at all.");
        jokesMap.put(2, "Doctor: \"I'm sorry but you suffer from a terminal illness and have only 10 to live.\"\n" +
                "Patient: \"What do you mean, 10? 10 what? Months? Weeks?!\"\n" +
                "Doctor: \"Nine.\"");
        jokesMap.put(3, "A man asks a farmer near a field, “Sorry sir, would you mind if I crossed your field instead of going around it? You see, I have to catch the 4:23 train.”\n" +
                "The farmer says, “Sure, go right ahead. And if my bull sees you, you’ll even catch the 4:11 one.” ");
        jokesMap.put(4, "Knock knock.\n" +
                "\n" +
                "Who’s there?\n" +
                "\n" +
                "Déja.\n" +
                "\n" +
                "Déja who?\n" +
                "\n" +
                "Knock knock.");
        jokesMap.put(5, "My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away.");
        jokesMap.put(6, "What happens when you cross a snowman and a vampire? \n" +
                "-\n" + "A: You get a frostbite.");
        jokesMap.put(7, "Mother, “How was school today, Patrick?”\n" +
                "Patrick, “It was really great mum! Today we made explosives!”\n" +
                "Mother, “Ooh, they do very fancy stuff with you these days. And what will you do at school tomorrow?”\n" +
                "Patrick, “What school?”");
        jokesMap.put(8, "How can you open a banana? \n" + "-\n" + "With a monkey! ");
        jokesMap.put(9, "Why are frogs such happy animals? \n" + "-\n" + "Because they always eat whatever bugs them.");
        return jokesMap.get(jokeNum);
    }
}


