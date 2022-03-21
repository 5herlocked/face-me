import 'package:flutter/material.dart';

class RegisterUserPage extends StatefulWidget {
  RegisterUserPage({Key? key}) : super(key: key);

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
            child: Text("Register"),
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
