import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class SignInPage extends StatefulWidget {
    SignInPage({Key? key}) : super(key: key);

    @override
    _SignInPageState createState() => _SignInPageState();
}

class _SignInPageState extends State<SignInPage> {
    String userName = "";
    String password = "";

    @override
    Widget build(BuildContext context) {
        return Scaffold(
            body: Center(
                child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                        TextInput(),
                        TextInput(),
                        Button(),
                        Button(),
                    ]
                )
            )
        )
    }
}