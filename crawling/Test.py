# step1. 관련 패키지 및 모듈 불러오기
import traceback
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import pandas as pd
import time

# step2. 네이버 뉴스 댓글정보 수집 함수
def get_naver_news_comments(name, wait_time=20, delay_time=0.1):
    
    # 크롬 드라이버로 해당 url에 접속
    driver = webdriver.Chrome('./chromedriver')
    
    # (크롬)드라이버가 요소를 찾는데에 최대 wait_time 초까지 기다림 (함수 사용 시 설정 가능하며 기본값은 5초)
    driver.implicitly_wait(wait_time)
    
    # 인자로 입력받은 url 주소를 가져와서 접속
    driver.get("https://www.joongang.co.kr/")
    
    #네이버 검색창 이름으로 가져오기
    search = driver.find_element_by_class_name("btn_search")
    
    #
#     search.clear()

    #검색창 열기
    search.click()
    
    #검색창 입력가능상태로만들기
    writable = driver.find_element_by_class_name("input_group")
    writable.click()
    
    #검색창에 값입력 후 검색 ~의원
    search =driver.find_element_by_class_name("form_control")
    search.send_keys(name+" 의원")
    search.send_keys(Keys.RETURN)
    
    #뉴스기사 전체보기 클릭

    driver.find_element_by_xpath('//*[@id="tab1"]/button/a').click()
    time.sleep(1)
    

    #정확도순으로 변경
    link=driver.find_element_by_xpath('//*[@id="dropdown"]/ul/li[2]/a').get_attribute("href")
    driver.get(link)
    time.sleep(1)

    
    #뉴스기사들어가기
    
    list_comments=[]
    list_names=[]
    
    
    for i in range(1,100):
        try:
            
            news = driver.find_element_by_xpath(f'//*[@id="container"]/section/div/section/ul/li[{i}]/div/h2/a')
            link = news.get_attribute("href")

            driver.execute_script('window.open("https://google.com");')  #구글 창 새 탭으로 열기
            time.sleep(1)

            driver.switch_to.window(driver.window_handles[-1])  #새로 연 탭으로 이동
            driver.get(link)
            time.sleep(2)

            # 댓글 크롤링 부분

            #댓글 전체보기
            driver.find_element_by_id("comment_more").send_keys(Keys.RETURN)
            time.sleep(1)

            #댓글 스크롤 끝까지 내리기
            last_height = driver.execute_script("return document.querySelector('.comment_body').scrollHeight")

            while True:
                driver.execute_script("document.querySelector('.comment_body').scrollTo(0,document.querySelector('.comment_body').scrollHeight)")

                time.sleep(1)

                new_height = driver.execute_script("return document.querySelector('.comment_body').scrollHeight")
                if new_height == last_height:
                    break
                last_height = new_height

                time.sleep(1)

            #댓글긁ㅇ모으기
            comments = driver.find_elements_by_class_name("comment_text")
            
            for comment in comments:
                list_comments.append(comment.text)
                
                list_names.append(name)
                time.sleep(1)
                
           
            


            #새탭 끄고 원래화면(기사목록)으로 돌아가기
            driver.close()

            driver.switch_to.window(driver.window_handles[0])

            
        except Exception as e:
            print(traceback.format_exc())
            break
    

    list_sum = list(zip(list_names,list_comments))
    
    driver.switch_to.window(driver.window_handles[0])
    driver.close()  
    return list_sum










            
            


        
    
# step3. 실제 함수 실행 및 csv로 저장
if __name__ == '__main__': # 설명하자면 매우 길어져서 그냥 이렇게 사용하는 것을 권장
    
    # 원하는 국회의원 이름 입력
    name_list = ['강기윤', '강대식', '강득구', '강민국', '강민정', '강병원', '강선우', '강은미', '강준현', '강훈식', '고민정', '고영인', '고용진', '구자근', '권명호', '권성동', '권영세', '권은희', '권인숙', '권칠승', '기동민', '김경만', '김경협', '김교흥', '김기현', '김남국', '김도읍', '김두관', '김미애', '김민기', '김민석', '김민철', '김병기', '김병욱', '김병욱', '김병주', '김상훈', '김상희', '김석기', '김선교', '김성원', '김성주', '김성환', '김수흥', '김승남', '김승수', '김승원', '김영배', '김영선', '김영식', '김영주', '김영진', '김영호', '김예지', '김용민', '김용판', '김웅', '김원이', '김윤덕', '김의겸', '김정재', '김정호', '김종민', '김주영', '김진표', '김철민', '김태년', '김태호', '김학용', '김한규', '김한정', '김형동', '김홍걸', '김회재', '김희곤', '김희국', '남인순', '노용호', '노웅래', '도종환', '류성걸', '류호정', '맹성규', '문정복', '문진석', '민병덕', '민형배', '민홍철', '박광온', '박대수', '박대출', '박덕흠', '박범계', '박병석', '박상혁', '박성민', '박성준', '박성중', '박수영', '박영순', '박완주', '박용진', '박재호', '박정', '박정하', '박주민', '박진', '박찬대', '박형수', '박홍근', '배준영', '배진교', '배현진', '백종헌', '백혜련', '변재일', '서동용', '서범수', '서병수', '서삼석', '서영교', '서영석', '서일준', '서정숙', '설훈', '성일종', '소병철', '소병훈', '송갑석', '송기헌', '송석준', '송언석', '송옥주', '송재호', '신동근', '신영대', '신원식', '신정훈', '신현영', '심상정', '안규백', '안민석', '안병길', '안철수', '안호영', '양경숙', '양금희', '양기대', '양이원영', '양정숙', '양향자', '어기구', '엄태영', '오기형', '오영환', '용혜인', '우상호', '우원식', '위성곤', '유경준', '유기홍', '유동수', '유상범', '유의동', '유정주', '윤건영', '윤관석', '윤두현', '윤미향', '윤상현', '윤영덕', '윤영석', '윤영찬', '윤재갑', '윤재옥', '윤주경', '윤준병', '윤창현', '윤한홍', '윤호중', '윤후덕', '이개호', '이달곤', '이동주', '이만희', '이명수', '이병훈', '이상민', '이상헌', '이성만', '이소영', '이수진', '이수진', '이양수', '이용', '이용빈', '이용선', '이용우', '이용호', '이원욱', '이원택', '이은주', '이인선', '이인영', '이장섭', '이재명', '이재정', '이정문', '이종배', '이종성', '이주환', '이채익', '이철규', '이탄희', '이태규', '이학영', '이해식', '이헌승', '이형석', '인재근', '임병헌', '임오경', '임이자', '임종성', '임호선', '장경태', '장동혁', '장제원', '장철민', '장혜영', '전봉민', '전용기', '전재수', '전주혜', '전해철', '전혜숙', '정경희', '정동만', '정성호', '정우택', '정운천', '정일영', '정점식', '정진석', '정찬민', '정청래', '정춘숙', '정태호', '정필모', '정희용', '조경태', '조명희', '조수진', '조승래', '조오섭', '조은희', '조응천', '조정식', '조정훈', '조해진', '주철현', '주호영', '지성호', '진선미', '진성준', '천준호', '최강욱', '최기상', '최승재', '최연숙', '최영희', '최인호', '최재형', '최종윤', '최춘식', '최형두', '최혜영', '추경호', '태영호', '하영제', '하태경', '한기호', '한무경', '한병도', '한정애', '한준호', '허영', '허은아', '허종식', '홍기원', '홍문표', '홍석준', '홍성국', '홍영표', '홍익표', '홍정민', '황보승희', '황운하', '황희']

    final_list =[]
    
    for name in name_list:
        try:
            # 함수 실행
            comments = get_naver_news_comments(name)
            print(comments)
            final_list.extend(comments)
        except Exception as e:
            print(traceback.format_exc())
            continue
        
        
        
    #     # 엑셀의 첫줄에 들어갈 컬럼명
    col = ['이름','내용']
      
    #     # pandas 데이터 프레임 형태로 가공
    df = pd.DataFrame(final_list, columns=col)
    
    
#     # 데이터 프레임을 csv로 저장 
    df.to_csv(f'news_crawled.csv', index=False)    

    