# Task

Based on the [product description](product-description.md) do the tasks below.

You should not spend much more or less than **four** hours for the solution of this task and the preparation of your presentation. If you can't do the implementation for all tasks, prepare a theoretical solution and present the concept to us.

1. Execute the existing tests and fix them. (./gradlew :backend:test)
1. Write user stories and implement the following features:
    1. The user can link a device to its account.
    1. A device can send the energy consumption to the backend.
    1. The user can define a threshold of energy consumption for its devices. A push notification is sent to the user when the threshold is exceeded. You don't need to implement the push notification itself - a dummy implementation is good enough. We don't need to see the integration with a service like firebase.
1. Make a plan what else needs to be considered and setup to release this project as a minimum sellable product. Consider things like deployment, operation, security, scalability etc. What is important to you and which next steps would you prioritize? 
