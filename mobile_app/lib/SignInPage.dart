import 'package:flutter/material.dart';

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
          TextField(
            decoration: InputDecoration(
              hintText: "User Name",
            ),
            onChanged: (value) {
              userName = value;
            },
          ),
          TextField(
            decoration: InputDecoration(
              hintText: "Password",
            ),
            onChanged: (value) {
              password = value;
            },
          ),
          MaterialButton(
            child: Text("Sign In"),
            onPressed: () {
              Navigator.pop(context, {
                "userName": userName,
                "password": password,
              });
            },
          ),
        ])));
  }
}
