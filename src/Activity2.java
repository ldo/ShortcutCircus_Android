package nz.gen.geek_central.ShortcutCircus;
/*
    "The excitement at the circus was in tents"

    ShortcutCircus -- demonstration of all the ways I know of to use
    (and abuse) Android shortcuts. Just for fun, this activity shows
    up as both an item in the list of launchable apps and in the list
    of items that can be selected to create shortcuts. It decides
    which function to perform based on the intent that was passed to
    it at launch time.

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
            for
              (
                String[] Entry : new String[][]
                    {
                        {"Say “Aah”", "Aah"},
                        {"Say nothing", null},
                    }
              )
              {
                final android.widget.Button CreateShortcut = new android.widget.Button(this);
                final String Label = Entry[0];
                final String ExtraMessage = Entry[1];
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
                            final Intent ShortcutIntent = new Intent(Intent.ACTION_MAIN);
                            ShortcutIntent.setClass(Activity2.this, Activity3.class);
                            if (ExtraMessage != null)
                              {
                                ShortcutIntent.putExtra(Activity3.MessageID, ExtraMessage);
                              } /*if*/
                            Activity2.this.sendBroadcast
                              (
                                new Intent
                                  (
                                    "com.android.launcher.action.INSTALL_SHORTCUT"
                                      /* no symbolic name for this in official APIs */
                                  )
                                    .putExtra(Intent.EXTRA_SHORTCUT_NAME, Label)
                                    .putExtra(Intent.EXTRA_SHORTCUT_INTENT, ShortcutIntent)
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
                            Activity2.this.finish();
                          } /*onClick*/
                      } /*OnClickListener*/
                  );
                MainLayout.addView(CreateShortcut, ButtonLayout);
              } /*for*/
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
