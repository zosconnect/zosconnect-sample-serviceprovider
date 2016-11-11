## Sample z/OS Connect Enterprise Edition Service Provider

A z/OS Connect Enterprise Edition (EE) Service Provider which demonstrates how to build a
service provider and include it as part of a z/OS Connect EE configuration.

### Pre-reqs

* [WebSphere Liberty](http://wasdev.net) installed locally
* The z/OS Connect EE SPI JAR file downloaded from a z/OS Connect EE install.

### Installing

* Clone this repository `git clone git://github.com/zosconnect/zosconnect-sample-provider.git`
* Update `build.xml` to point to the location of your Liberty install and the SPI jar.
* Build the feature `ant build.xml`
* Install the feature into your z/OS Connect EE environment `wlp/bin/featureManager install com.ibm.zosconnect.sample.provider.feature-1.0.0.0.esa`

### Configuring

* Add the following to the `featureManager` section
```
<feature>usr:sampleProvider</feature>
```
* Create an service definition
```
<zosconnect_zosConnectService id="sampleService" serviceName="sampleService"
                           serviceRef="dateService" serviceDescription="">
</zosconnect_zosConnectService>
<usr_sampleService id="dateService" timezone="GMT"/>
```

### Updating

If you want to use this sample as the basis of your own service provider, or add extra functionality to
this service provider then the contents of the `src` directory can be imported into an Eclipse installation
which has the WDT tools installed.

### Notice

&copy; Copyright IBM Corporation 2016

### License
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
