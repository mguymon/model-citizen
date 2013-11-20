## Model Citizen Changelog

### 0.6.0 (November 19, 2013)

* Add AfterCreateCallback
* Remove need for @NewInstance, just checks for ConstructorCallback directly
* Create new callback package, deprecate old ConstructorCallback and FieldCallback

### 0.5.0 (October 8, 2013)

* Allow for inheritenace in Blueprints ([Issue #16](https://github.com/mguymon/model-citizen/issues/16))

#### 0.5.1 (October 8, 2013)

* Fix ordering of Blueprint properties so implementation properties overrides inherited.

#### 0.5.2 (November 1, 2013

* Update SLF4J ([Pull #17](https://github.com/mguymon/model-citizen/pull/17)) [thanks [salbito](https://github.com/salbito)]

### 0.4.0 (June 5, 2013)

* Sign artifact only when deploying, not when installing ([Pull #12](https://github.com/mguymon/model-citizen/pull/12)) [thanks [mttkay](https://github.com/mttkay)]
* Never call blueprint constructors more than once ([Pull #13](https://github.com/mguymon/model-citizen/pull/13)) [thanks [mttkay](https://github.com/mttkay)]

