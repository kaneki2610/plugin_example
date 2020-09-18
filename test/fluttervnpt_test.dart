import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:fluttervnpt/fluttervnpt.dart';

void main() {
  const MethodChannel channel = MethodChannel('fluttervnpt');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });
}