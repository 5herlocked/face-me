import 'package:flutter/material.dart';

class RegisterUserPage extends StatefulWidget {
    RegisterUserPage({Key? key, required this.title}) : super(key: key);

    @override
    _RegisterUserPageState createState() => _RegisterUserPageState();
}

class _RegisterUserPageState extends State<RegisterUserPage> {
    String userName = "";
    String password = "";

    @override
    Widget build(BuildContext context) {
        return Scaffold(
            body: Center(
                child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                        TextBox(),
                        TextBox(),
                        Button()
                    ]
                )
            )
        )
    }
}