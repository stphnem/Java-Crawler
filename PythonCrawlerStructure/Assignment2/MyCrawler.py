'''
@Author: Rohan Achar ra.rohan@gmail.com
'''
##This is an open source framework at https://github.com/Mondego/crawler4py
##For INF 141 or CS 121 assignment, make changes to the below code if required


from Crawler4py.Crawler import Crawler
from SampleConfig import SampleConfig

crawler = Crawler(SampleConfig())

print (crawler.StartCrawling())

exit(0)
