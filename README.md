# victorWeatherapp
automous challenge
# dependencies using: 
- Glide
- Retrofit,
- kotlin Extension,
- Gson, Realm
- backend endpoint: opencagedata.com & openweathermap.org

# My App Architecture:
MVVM,
clean project: Domain, Presentation, Data
# detail:
- all functional & Base Class, & Helper Class in my utils module
- app package: UI for app & some presentation component
# Logic UI & flow:  
- trigger event search -> request viewModel load data City with lat& lon By excute UseCaseClass
- useCaseClass call an function Retrofit with observable 
- response city with latlong- > throug to request viewModel load weather city in NetworkService class
- result of weather will be truncate & transform in subcribe process background in NetWorkService class . so not to transform data for UI friendly in Adapter where it will be transform data many times by notifychanges excuted cause performance low
# Search Text:
- all key search will be saved by realm any new keyword
- show suggest word for input city
