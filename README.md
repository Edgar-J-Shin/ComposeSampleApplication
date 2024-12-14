# LezhinApplication

Kotlin
- Cold Flow와 Hot Flow에 대해 자세히 설명해 주세요.
    - Cold Flow는 데이터 스트림이 구독되기 전에는 아무 작업도 진행되지 않고 구독 이후에 데이터 배출이 시작됩니다.
    - How Flow는 구독자가 없어도 데이터를 계속 배출하고 유지합니다. 구독자가 구독을 시작한 시점부터 발생한 데이터를 받게 됩니다. 
- StateFlow와 SharedFlow에 대해 자세히 설명해 주세요.
    - StateFlow 는 데이터 스트림의 역할만을 하는 Flow의 한계를 극복하여 Flow + Data holder 기능을 가지고 있습니다. 초기 값을 가지며 이전에 내보낸 값과 동일한 값은 방출하지 않고 최신 상태를 유지하는 데이터 홀더로써 사용을 합니다. 그리고 상태를 관리할 때 주로 사용합니다.
    - SharedFlow는 초기 값을 가지지 않고 버퍼 전략을 사용하여 이전 이벤트를 전달하거나 연속해서 발생하는 이벤트를 처리하게 됩니다.  그리고 이벤트를 처리할 때 주로 사용합니다.

Android ViewModel 관련
- owner에 대해 자세히 설명해 주세요
    - ViewModel을 생성하고 관리할 때 ViewModel은 특정 LifecycleOwner의 생명주기를 따르게 됩니다. ViewModelProvider.Factory 를 생성할 때 Activity 또는 Fragment를 owner로써 전달하며 NavHostController에서는 NavBackStackEntry가 owner로써 전달됩니다.
    - owner의 생명주기에 맞춰 ViewModel의 생성, 유지, 소멸 시점이 결정됩니다.
- Compose Navigation 과 Dagger Hilt 를 같이 사용하는 경우, hiltViewModel()로 ViewModel instance를 가져올 때, owner를 어떻게 설정해야 하는지 자세히 설명해 주세요.
    - hiltViewModel()은 Dagger Hilt를 통해 ViewModel instance를 주입받는 함수인데 현재 Navigation의 NavBackStackEntry를 owner로써 사용합니다. 각 Route는 각각의 NavBackStackEntry를 가지며 이를 통해 각 Route 별로 ViewModel을 생성하고 관리하게 됩니다.
    - 기본적으로 hiltViewModel() 내부에서 NavBackStackEntry를 owner로써 사용하고 있으며 개발자가 설정하지 않지만 ViewModel의 owner로 Activity 또는 Fragment를 사용할 경우 명시적을 설정할 수 있습니다.

Android Paging3 관련
- PagingSource와 getRefreshKey 함수의 파라미터와 리턴 값에 대해 자세히 설명해 주세요.
    - PagingState는 현재 Paging 시스템의 상태를 나타내는 객체로, 다음과 같은 정보를 가지고 있습니다.
        - anchorPosition: 현재 사용자가 보고 있는 목록의 중앙 위치
        - pages: 이미 로드된 모든 데이터 목록
        - closestPageToPosition(position: Int): 특정 위치에 가장 가까운 페이지 정보
    - Key는 새로고침 시에 어느 페이지의 key로부터 다시 로드할 지 결정하는데 사용합니다.
- PagingSource의 load 함수의 파라미터와 리턴 값에 대해 자세히 설명해 주세요.
    - LoadParams<Key>는 데이터를 로드하는 데 필요한 정보가 포함된 객채로, 다음과 같은 정보를 가지고 있습니다.
        - key: 데이터를 로드하기 위해 사용할 키
        - loadSize: 요청할 데이터의 크기
        - placeholdersEnabled: Placeholder를 사용할지 여부
- LoadResult<Key, Value>는 데이터 로드 결과를 나타내고 다음과 같은 정보를 가지고 있습니다.
    - LoadResult.Page: 성공적으로 데이터를 로드한 경우
    - LoadResult.Error: 데이터 로드에 실패한 경우
    - LoadResult.Invalid: 유효하지 않은 데이터일 경우
