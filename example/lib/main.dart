import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:fluttervnpt/fluttervnpt.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _deviceInfo = "empty";
  String number = "";
  StreamSubscription _subscription;
  TextEditingController controller = TextEditingController();
  bool _validate = false;

  @override
  void initState() {
    super.initState();
    // this.initPlatformState();
  }

/*
  Future<void> initPlatformState() async {
    try {
      this._subscription = FlutterVnpt().demoEventChannel((msg) {
        setState(() {
          this.number = msg;
        });
      });
    } on PlatformException catch (e) {
      print(e.message);
    }
    setState(() {});
  }
*/

  @override
  void dispose() {
    super.dispose();
    this._subscription.cancel();
    this.controller.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Container(
            margin: EdgeInsets.only(left: 8, right: 8),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Text(
                      "2. Demo method channel:",
                      style:
                          TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: Colors.green),
                    ),
                    SizedBox(height: 8),
                    Text(
                      "Value:   ${this._deviceInfo}",
                      style: TextStyle(
                          color: Colors.redAccent, fontSize: 16, fontWeight: FontWeight.bold),
                    ),
                    SizedBox(height: 8),
                    TextField(
                        controller: controller,
                        decoration: InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter the Value',
                          errorText: this._validate ? 'Value Can\'t Be Empty' : null,
                        ))
                  ],
                ),
                SizedBox(height: 16),
                RaisedButton(
                  padding: EdgeInsets.all(8),
                  onPressed: () {
                    this._getDeviceInfo();
                  },
                  child: Text("Start activity and receive data from Android native"),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }

  void _getDeviceInfo() async {
    print(controller.text);
    if (controller.text.isEmpty) {
      setState(() {
        this._validate = true;
      });
    } else {
      setState(() {
        this._validate = false;
      });
      try {
        Map<String, dynamic> param = {};
        param["type"] = this.controller.text;
        String result = await FlutterVnpt().getDeviceInfo(param);
        if (result != null) {
          this._deviceInfo = result;
        } else {
          this._deviceInfo = "null";
        }
        this.controller.clear();
        setState(() {});
      } on PlatformException catch (e) {
        this._deviceInfo = e.message;
      }
    }
  }
}
