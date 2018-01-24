# z/OS Connect Enterprise Edition Sample Service Provider

In this code we show how to create a Service Provider for z/OS Connect Enterprise Edition (EE). A service provider is the mechanism by which z/OS resources can be made available to be exposed as RESTful APIs through z/OS Connect EE.

![z/OS Connect EE Architecture](./imgs/ss-done.png)

## Scenario

Data records are stored in a document database which need to be included into new RESTful APIs hosted in z/OS Connect EE. By creating a service provider the documents in a specific table can be made available to the API developer for inclusion in the API.

### 1. Identify the resources to make available

This is hte most important step of designing your service provider, it is best to try and expose a sepcific resource through a service. Our service provider could decide to expose the entire database as a service, but then the API developer needs to specify the table and deal with the differing record structures that each table may contain. It is sbetter to expose a single table as a service which contains a single record type and then have multiple services if there are multiple tables that need to be included in the APIs.

### 2. Produce schemas for the service

The input and output of every z/OS Connect EE service is a JSON Object and these objects have a structure defined by a JSON schema which the service makes available. The generation of these schemas is done as part of the creation of the service using meta-data about the resource the service developer has avaialble to them.

### 3. Define the service configuration

The schemas are not the only pieces of information that a service will need when invoked, for services accessing the database the database table will be defined in teh SAR.

The connection configuration is not stored in the SAR file, as this information may change depending on the environment the service is deployed into, development, test, production and it is best to be able to deploy the same SAR file in each of these environments without having to rebuild it.

Building the SAR file is covered in 

`TODO link to SAR plugin repo`

### 4. Create the service provider code

The service provider needs to provide implementations of two interfaces defined in the z/OS Connect EE SPI. These are

`TODO add links to JavaDoc`

- ServiceFactory - An OSGi service which will process the SARs for this Service Provider.
- ServiceController - Instances of this class will represent the services in the z/OS Connect EE server.

#### 4.1 Creating a ServiceFactory

The ServiceFactory interface defines methods that will be called in relation to changes in a SAR file.

##### registerService

This method is called when a SAR file is newly made available to the z/OS Connect EE server. The contents of the SAR file are available in the SarFile object and any configuration based overrides are available in the properties object passed in as parameters to this function.

When called this method should:

1. Check the contents of the SAR file to make sure they're still valid.
1. Create an instance of the ServiceController to represent the service and set the configuration appropriately.
1. Register the ServiceController instance with the OSGi registry to install the service.

##### updateService

This method is called when a SAR file for a currently installed service is modified and the server has been notified of the change.

When called this method should:

1. Locate the ServiceController instance for the SAR file being updated.
1. Update the configuration in that Object.

##### deregisterService

This method is called when a SAR file is removed and the server has been notified of the change.

When called this method should:

1. Deregister the ServiceController that represents the Service.
1. Clean up any other resources associated with the Service.

#### 4.2 Creating a ServiceController

An instance of the ServiceController represents the service that was defined in the SAR file. The required methods should be implemented as follows:

##### 