I use Finnhub API. This API has a limit on the number of requests per minute. If they are exceeded, there will be an HTTP 429 error. Promotions that cannot be loaded will be under the ProgressBar.

The application consists of three fragments: 1 - List - a list of SP500 stocks 2 - Favorites - a list of favorite stocks 3 - News - news of the entire market

When you click on the stock item in the 1st and 2nd fragments, the Chart fragment will open and the stock price chart will appear. On the price chart, you can choose the price scale - for months or for years. When you click on the graphs, a price window appears at the point where you clicked. Closing this fragment through the button "Back". You can search for shares in the 1st and 2nd fragments through EditText. You can add to favorites and remove from it by pressing the "star".

I use RxJava, Room, LiveData, ViewModel, RecyclerView, Paging, Retrofit2.
