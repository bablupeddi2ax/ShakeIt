package com.example.shakeit;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class Translator {
    // Create an English-German translator:

    TranslatorOptions options =
            new TranslatorOptions.Builder()
                    .setSourceLanguage(TranslateLanguage.ENGLISH)
                    .setTargetLanguage(TranslateLanguage.GERMAN)
                    .build();
    final Translator englishGermanTranslator = (Translator) Translation.getClient(options);
    DownloadConditions conditions = new DownloadConditions.Builder().requireWifi().build();



























}
