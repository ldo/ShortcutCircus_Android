package nz.gen.geek_central.ShortcutCircus;
/*
    "The excitement at the circus was in tents"

    ShortcutCircus -- demonstration of all the ways I know of to use
    (and abuse) Android shortcuts.

    This particular activity doesn't do much, it's just to demonstrate
    that you can have more than one app icon appear in the launcher
    from a single installed package.

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

public class Activity1 extends android.app.Activity
  {

    @Override
    public void onCreate
      (
        android.os.Bundle SavedInstanceState
      )
      {
        super.onCreate(SavedInstanceState);
        final android.widget.TextView Display = new android.widget.TextView(this);
        Display.setText("Hi, I'm ShortcutCircus.Activity1");
        setContentView(Display);
      } /*onCreate*/

  } /*Activity1*/
