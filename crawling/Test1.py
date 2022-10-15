#             #댓글펼치기
#             driver.find_element_by_id("comment_more").send_keys(Keys.ENTER)

#             #댓글출력
#             for j in range(1,11):

#                 try:
#                     comment = driver.find_element_by_xpath(f'//*[@id="layer_comment"]/div[2]/div/ul/li[{j}]/div/div[2]/p')
#                     list_comments.append(comment.text)
#                 except:
#                     continue



#             driver.back()

            
#         except:
#             continue
            
#     for comment in list_comments:
#         print(comment)
    
    
    
    # 키 이벤트 전송
    # 태그가 input 태그이므로 입력창에 키이벤트가 전달되면, 입력값이 자동으로 작성됨
#     search.send_keys(name+" 의원")

    # 엔터 입력
#     search.send_keys(Keys.RETURN)
    
    #뉴스탭 선택
#     continue_link = driver.find_element_by_link_text('뉴스')
    
    #뉴스탭 클릭
#     continue_link.click()
    
    #뉴스타이틀들 클래스로 가져오기
#     news_tit = driver.find_elements_by_class_name("news_tit")
    #뉴스댓글 뽑아오는 for문
#     for news in news_tit:
        #뉴스 타이틀중 하나 클릭해서 들어가기
#         switch_to.window(driver.window_handles[1]) --탭 전환하는 함수
#         news.click()
    
#         time.sleep(1)
        
#         driver.switch_to.window(driver.window_handles[1])
        
#         time.sleep(1)
        
#         driver.close()
        
#         time.sleep(1)
        
        #원래 탭으로 전환하는 함수
#         driver.switch_to.window(driver.window_handles[0])
        
#         time.sleep(3)