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

  @override
  void initState() {
    super.initState();
    this.initPlatformState();
  }

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

  @override
  void dispose() {
    super.dispose();
    this._subscription.cancel();
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
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Text(
                      "1. Demo Event channel:   ${this.number}",
                      style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: Colors.green),
                    ),
                    SizedBox(height: 8),
                    Text(
                      "2. Demo method channel:",
                      style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: Colors.green),
                    ),
                    SizedBox(height: 8),
                    Text(
                      "Device info:   ${this._deviceInfo}",
                      style:
                      TextStyle(color: Colors.redAccent, fontSize: 16, fontWeight: FontWeight.bold),
                    ),
                  ],
                ),
                SizedBox(height: 16),
                RaisedButton(
                  onPressed: () {
                    this._getDeviceInfo();
                  },
                  child: Text("Start activity and receive from Android native"),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }

  void _getDeviceInfo() async {
    try {
      Map<String, dynamic> param = {};
      param["type"] = "MODEL";
      String result = await FlutterVnpt().getDeviceInfo(param);
      if (result != null) {
        this._deviceInfo = result;
      } else {
        this._deviceInfo = "Can't get device";
      }
      setState(() {});
    } on PlatformException catch (e) {
      this._deviceInfo = e.message;
    }
  }
}
