# 패스워드 검증을 통한 TDD 실습
## TDD를 해야하는 이유
1. 문서화의 역할을 할 수 있다.
2. 코드에 결함을 발견할 수 있다.
3. 리팩토링 시 안정성을 확보할 수 있다. (심리적 안정감을 가질 수 있다.)
4. 테스트 하기 쉬운 코드를 작성하려는 노력이 더 낮을 결합도를 가진 설계를 이끌어낸다.

## TDD 실습

### 문서화 역할
다음과 같이 @DisplayName에 테스트의 목적을 적음으로써 문서화의 역할을 할 수 있다.

    @DisplayName("비밀번호가 최소 8자 이상, 12자 이하면 예외가 발생하지 않는다.")
<br>

### 코드의 결합 발견과 안정성 확보
다음과 같이 password.length를 리팩토링 했을 때 바로 테스트 할 수 있기 때문에 안정감을 가질 수 있으며 코드에 문제가 있을 경우 재빨리 찾을 수 있다.

    //리팩토링 이전 //
    if(password.length < 8 || password.length > 12) {
        throw new IllegalArgumentException("비밀번호는 최소 8자 이상 12자 이하여야 한다.");
    }

    // 리팩토링 이후 //
    int length = password.length();
    if(length < 8 || length > 12) {
        throw new IllegalArgumentException("비밀번호는 최소 8자 이상 12자 이하여야 한다.");
    }

<br>

### 결합도 낮은 설계가 가능해지는 이유
다음과 같이 테스트 케이스의 성공, 실패가 값 할당 조건에 따라 달라지는 경우 테스트를 하는 방법에 대해 알아보자.

    public class User {

        private String password;
    
        public void initPassworrd() {
            RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
            String randomPassword = randomPasswordGenerator.generatePassword();
    
            if(randomPassword.length() > 8 && randomPassword.length() < 12) {
                this.password = randomPassword;
            }
        }
    }

랜덤으로 생성한 패스워드가 8자리 이하이거나, 12자리 이상인 경우 password에는 값이 할당되지 않는다. 

    @Test
    void validateRandomPassword() {
        // given
        User user = new User();

        // when
        user.initPassworrd();

        // then
        assertThat(user.getPassword()).isNotNull();
    }

따라서 다음과 같은 테스트 코드를 작성하면 실패와 성공이 랜덤하게 나타난다. 이 코드를 리팩토링 하여 테스트 해보자. 다음과 같이 PasswordGeneratedPolicy 인터페이스를 만들고 
이를 RandomPassowrdGenerator가 상속 받게 만들자.

    public interface PasswordGeneratedPolicy {

        String generatePassword();
    }

그러면 다음과 같이 인터페이스로 구현체를 받을 수 있게 된다. 
    
    public void initPassworrd(PasswordGeneratedPolicy passwordGeneratedPolicy) {
        String randomPassword = passwordGeneratedPolicy.generatePassword();

        if(randomPassword.length() > 8 && randomPassword.length() < 12) {
            this.password = randomPassword;
        }
    }

따라서 다음과 같은 테스트 코드 작성이 가능해진다.

    @DisplayName("비밀번호가 최소 8자 이상, 12자 이하면 예외가 발생하지 않는다.")
    @Test
    void validateRandomPassword() {
        // given
        User user = new User();
    
        // when, then
        assertThatCode(() -> user.initPassworrd(new CorrectPassowrdGenerator()))
            .doesNotThrowAnyException();
    }

    @DisplayName("비밀번호가 8자 미만 또는 12자 초과하는 경우 IllegalArgumentException이 발생한다.")
    @Test
    void validateRandomPassword2() {
        // given
        User user = new User();
        
        // when, then
        assertThatCode(() -> user.initPassworrd(new WrongPasswordGenerator()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 최소 8자 이상 12자 이하여야 한다.");
    }


<br>

### 경계 조건에 대한 테스트 코드
TDD를 할 때는 다음과 같이 경계조건에 대한 테스트 코드를 꼭 작성해야 한다.

    @DisplayName("비밀번호가 8자 미만 또는 12자 초과하는 경우 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"aabbcce", "aabbccddeeffg"})
    void validatePasswordTest2(String password) {
        assertThatCode(() -> PasswordValidator.validate(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 최소 8자 이상 12자 이하여야 한다.");
    }
<br>

