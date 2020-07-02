# References
 * [Using Micronaut and GraphQL with transactions and security support](http://lifeinide.com/post/2019-04-15-micronaut-graphql-with-transaction-and-security-support/) [Github](https://github.com/l0co/micronaut-graphql)
 * [Micronaut AWS API Gateway Example](https://github.com/micronaut-projects/micronaut-aws/tree/master/examples/api-gateway-example)
 * [Github Action - AWS Lambda Deploy](https://github.com/marketplace/actions/aws-lambda-deploy)

### Initial Deploy as AWS Lambda
You can use the AWS CLI to quickly deploy your application to AWS Lambda and Amazon API Gateway with your SAM template.

You will need an S3 bucket to store the artifacts for deployment. Once you have created the S3 bucket, run the following command from the project's root folder - where the sam.yaml file is located:
```
aws cloudformation package --template-file sam.yaml --output-template-file output-sam.yaml --s3-bucket micronaut-person-deploy
```

As the command output suggests, you can now use the cli to deploy the application. Run the aws cloudformation deploy command from the output of the package command.
```
aws cloudformation deploy --template-file output-sam.yaml --stack-name micronaut-person-lambda --capabilities CAPABILITY_IAM
```

Once the application is deployed, you can describe the stack to show the API endpoint that was created. The endpoint should be the `micronaut-person-lambda` key of the `Outputs` property:
```
aws cloudformation describe-stacks --stack-name micronaut-person-lambda
```

For example, the client can post the GraphQL query to the following URL of AWS Lambda:
https://0wkpgk7b07.execute-api.ap-southeast-1.amazonaws.com/Prod/graphql

### Continuous Deploy with Github Action
For the detail of build pipeline, please refer to the [workflow file](.github/workflows/aws-lambda.yml).

### Local Development
Running test with the following command:
```
gradlew test -Dmicronaut.environments=dev
```

Running app with the following command:
```
gradlew run -Dmicronaut.environments=dev
```

