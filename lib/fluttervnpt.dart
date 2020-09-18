import 'dart:async';

import 'package:flutter/services.dart';

typedef void Listener(dynamic msg);

class FlutterVnpt {
  static const MethodChannel _channel = const MethodChannel('fluttervnpt');

  static const EventChannel _events = const EventChannel('locationStatusStream');
  final Stream<dynamic> stream = _events.receiveBroadcastStream();

  FlutterVnpt._privateConstructor();

  static final FlutterVnpt _instance = FlutterVnpt._privateConstructor();

  factory FlutterVnpt() {
    return _instance;
  }

  //MethodChannel
  Future<String> getDeviceInfo(Map<String, dynamic> value) async {
    final String info = await _channel.invokeMethod('startActivity', value);
    return info;
  }

  //EventChannel
  StreamSubscription demoEventChannel(Listener listener){
    var subscription = stream.listen(listener, cancelOnError: true);
    return subscription;
  }
}
