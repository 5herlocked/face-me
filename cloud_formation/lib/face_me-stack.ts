import { Stack, StackProps } from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as path from 'path';
import * as cdk from "aws-cdk-lib";
import * as iot from "aws-cdk-lib/aws-iot";
import * as iotEvents from "aws-cdk-lib/aws-iotevents";
import * as sns from "aws-cdk-lib/aws-sns";
import * as iam from "aws-cdk-lib/aws-iam";
import * as lambda from "aws-cdk-lib/aws-lambda";

export class FaceMeStack extends cdk.Stack {
  constructor(scope: cdk.App, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const edge_device = new iot.CfnThing(this, 'FaceMeThing', {
      thingName: 'FaceMe-Jetson'
    });

    const fn = new lambda.Function(this, 'FaceMeLambdaFunction', {
      runtime: lambda.Runtime.PYTHON_3_9,
      handler: 'main-face_me.lambda_handler',
      code: lambda.Code.fromAsset(path.join(__dirname, 'lambda'))
    });

    const iam_role = fn.role;

    fn.role?.addManagedPolicy(iam.ManagedPolicy.fromAwsManagedPolicyName('AWSLambdaDynamoDBExecutionRole'));
    fn.role?.addManagedPolicy(iam.ManagedPolicy.fromAwsManagedPolicyName('AWSIoTFullAccess'))
    // fn.role?.addManagedPolicy(iam.ManagedPolicy.fromAwsManagedPolicyName(''))
    // The code that defines your stack goes here

    // example resource
    // const queue = new sqs.Queue(this, 'CloudFormationQueue', {
    //   visibilityTimeout: cdk.Duration.seconds(300)
    // });
  }
}
