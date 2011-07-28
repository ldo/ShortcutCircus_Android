package nz.gen.geek_central.ShortcutCircus;
/*
    "The excitement at the circus was in tents"

    ShortcutCircus -- demonstration of all the ways I know of to use
    (and abuse) Android shortcuts.

    This activity is only invoked via shortcuts, to perform a custom
    action (in this case, just display a message) which is encoded
    in the shortcut.

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

public class Activity3 extends android.app.Activity
  {
    public static final String MessageID = "nz.gen.geek_central.ShortcutCircus.Message";

    @Override
    public void onCreate
      (
        android.os.Bundle SavedInstanceState
      )
      {
        super.onCreate(SavedInstanceState);
        final String ExtraMessage = getIntent().getStringExtra(MessageID);
        final android.widget.TextView Display = new android.widget.TextView(this);
        String Message = "Hi, I'm ShortcutCircus.Activity3";
        if (ExtraMessage != null)
          {
            Message += ", and I say “" + ExtraMessage + "”";
          }
        else
          {
            Message += ", and I don’t know what else to say";
          } /*if*/
        Message += ".";
        Display.setText(Message);
        setContentView(Display);
      } /*onCreate*/

  } /*Activity3*/
