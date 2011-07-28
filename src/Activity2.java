package nz.gen.geek_central.ShortcutCircus;
/*
    "The excitement at the circus was in tents"

    ShortcutCircus -- demonstration of all the ways I know of to use
    (and abuse) Android shortcuts. Just for fun, this activity shows
    up as both an item in the list of launchable apps and in the list
    of items that can be selected to create shortcuts. It decides
    which function to perform based on the intent that was passed to
    it at launch time.

    Theory of operation: the official docs are a bit thin, but useful
    details can be gleaned from the Android sources. The standard Launcher
    is here
    <http://android.git.kernel.org/?p=platform/packages/apps/Launcher.git;a=tree;f=src/com/android/launcher;hb=HEAD>.
    There is also a “Launcher2”, here
    <http://android.git.kernel.org/?p=platform/packages/apps/Launcher2.git;a=tree;f=src/com/android/launcher2;hb=HEAD>,
    but I’ll concentrate on the simpler one for now. Looking at the
    manifest
    <http://android.git.kernel.org/?p=platform/packages/apps/Launcher.git;a=blob;f=AndroidManifest.xml;hb=HEAD>,
    you can see broadcast receivers which accept intents with actions
    “com.android.launcher.action.INSTALL_SHORTCUT” and
    “com.android.launcher.action.UNINSTALL_SHORTCUT”, controlled
    by associated permissions. The corresponding receiver classes are
    InstallShortcutReceiver
    <http://android.git.kernel.org/?p=platform/packages/apps/Launcher.git;a=blob;f=src/com/android/launcher/InstallShortcutReceiver.java;hb=HEAD>
    and UninstallShortcutReceiver
    <http://android.git.kernel.org/?p=platform/packages/apps/Launcher.git;a=blob;f=src/com/android/launcher/UninstallShortcutReceiver.java;hb=HEAD>.
    From these, you can see how the intent parameters are actually
    processed.

    Copyright 2011 by Lawrence D'Oliveiro <ldo@geek-central.gen.nz>.

    Licensed under the Apache License, Version 2.0 (the "License"); you
    may not use this file except in compliance with the License. You may
    obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
    implied. See the License for the specific language governing
    permissions and limitations under the License.
*/

import android.content.Intent;

public class Activity2 extends android.app.Activity
  {
    private static final String EXTRA_SHORTCUT_DUPLICATE = "duplicate";
      /* boolean for telling launcher whether to allow/remove duplicates or not;
        if omitted, default is true. */

    private class ShortcutIntent
      {
        public final String Label;
        public final Intent DoWhat;

        public ShortcutIntent
          (
            String Label,
            Intent DoWhat
          )
          {
            this.Label = Label;
            this.DoWhat = DoWhat;
          } /*ShortcutIntent*/

      } /*ShortcutIntent*/

    private ShortcutIntent[] DefineShortcuts()
      /* builds the list of shortcuts I know how to define. */
      {
        final java.util.ArrayList<ShortcutIntent> Shortcuts =
            new java.util.ArrayList<ShortcutIntent>();
        for
          (
            String[] Entry : new String[][]
                {
                    {"Say “Aah”", "Aah"},
                    {"Say “Isn’t This Wonderful?”", "Isn’t This Wonderful?"},
                    {"Say nothing", null},
                }
          )
          {
          /* intents that launch Activity3 to display different message strings */
            final String Label = Entry[0];
            final String ExtraMessage = Entry[1];
            final Intent DoWhat = new Intent(Intent.ACTION_MAIN);
            DoWhat.setClass(this, Activity3.class);
            if (ExtraMessage != null)
              {
                DoWhat.putExtra(Activity3.MessageID, ExtraMessage);
              } /*if*/
            Shortcuts.add(new ShortcutIntent(Label, DoWhat));
          } /*for*/
        Shortcuts.add
          /* just for fun, a shortcut that doesn't even point to any
            of my activities, to show it can be done */
          (
            new ShortcutIntent
              (
                "Find Lawrence On GitHub",
                new Intent
                  (
                    Intent.ACTION_VIEW,
                    new android.net.Uri.Builder()
                        .scheme("https")
                        .encodedPath("//github.com/ldo")
                        .build()
                  )
              )
          );
        return
            Shortcuts.toArray(new ShortcutIntent[Shortcuts.size()]);
      } /*DefineShortcuts*/

    @Override
    public void onCreate
      (
        android.os.Bundle SavedInstanceState
      )
      {
        super.onCreate(SavedInstanceState);
        final Intent WhatToDo = getIntent();
        String Action = WhatToDo.getAction();
        if (Action != null)
          {
            Action = Action.intern();
          } /*if*/
        if (Action == Intent.ACTION_CREATE_SHORTCUT)
          {
          /* launched to create a shortcut */
            final android.widget.LinearLayout MainLayout = new android.widget.LinearLayout(this);
            MainLayout.setOrientation(android.widget.LinearLayout.VERTICAL);
            setContentView(MainLayout);
            final android.view.ViewGroup.LayoutParams ButtonLayout =
                new android.view.ViewGroup.LayoutParams
                  (
                    android.view.ViewGroup.LayoutParams.FILL_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                  );
              {
                final android.widget.TextView Caption = new android.widget.TextView(this);
                Caption.setText("Shortcuts to add:");
                MainLayout.addView(Caption, ButtonLayout);
              }
            for (ShortcutIntent Entry : DefineShortcuts())
              {
                final android.widget.Button CreateShortcut = new android.widget.Button(this);
                final String Label = Entry.Label;
                final Intent ShortcutIntent = Entry.DoWhat;
                CreateShortcut.setText(Label);
                CreateShortcut.setOnClickListener
                  (
                    new android.view.View.OnClickListener()
                      {
                        @Override
                        public void onClick
                          (
                            android.view.View TheView
                          )
                          {
                            Activity2.this.sendBroadcast
                              (
                                new Intent
                                  (
                                    "com.android.launcher.action.INSTALL_SHORTCUT"
                                      /* no symbolic name for this in official APIs */
                                  )
                                    .putExtra(Intent.EXTRA_SHORTCUT_NAME, Label)
                                    .putExtra(Intent.EXTRA_SHORTCUT_INTENT, ShortcutIntent)
                                  /* icon is optional, launcher will provide a default if omitted */
                                    .putExtra
                                      (
                                        Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                                        Intent.ShortcutIconResource.fromContext
                                          (
                                            Activity2.this,
                                            R.drawable.icon
                                          )
                                      )
                                      /* or Intent.EXTRA_SHORTCUT_ICON and pass a Bitmap */
                              );
                              /* if EXTRA_SHORTCUT_DUPLICATE is passed/defaulted to true
                                here, launcher will create the shortcut even if a duplicate
                                already exists */
                            Activity2.this.finish();
                          } /*onClick*/
                      } /*OnClickListener*/
                  );
                MainLayout.addView(CreateShortcut, ButtonLayout);
              } /*for*/
              {
                final android.widget.Button DeleteShortcuts = new android.widget.Button(this);
                DeleteShortcuts.setText("Delete All My Shortcuts");
                DeleteShortcuts.setOnClickListener
                  (
                    new android.view.View.OnClickListener()
                      {
                        @Override
                        public void onClick
                          (
                            android.view.View TheView
                          )
                          {
                            for (ShortcutIntent Entry : DefineShortcuts())
                              {
                                Activity2.this.sendBroadcast
                                  (
                                    new Intent
                                      (
                                        "com.android.launcher.action.UNINSTALL_SHORTCUT"
                                          /* no symbolic name for this in official APIs */
                                      )
                                        .putExtra(Intent.EXTRA_SHORTCUT_NAME, Entry.Label)
                                        .putExtra(Intent.EXTRA_SHORTCUT_INTENT, Entry.DoWhat)
                                  );
                                  /* if EXTRA_SHORTCUT_DUPLICATE is passed/defaulted to true
                                    here, launcher will remove all matches, otherwise only
                                    first match */
                              } /*for*/
                            Activity2.this.finish();
                          } /*onClick*/
                      } /*OnClickListener*/
                  );
                MainLayout.addView(DeleteShortcuts, ButtonLayout);
              }
          }
        else
          {
          /* assume launch as an app */
            final android.widget.TextView Display = new android.widget.TextView(this);
            Display.setText
              (
                    "Hi, I'm ShortcutCircus.Activity2. Look for me in the"
                +
                    " “add shortcut” section of your launcher."
              );
            setContentView(Display);
          } /*if*/
      } /*onCreate*/

  } /*Activity2*/
