//
//  MyView.swift
//  FillScreen
//
//  Created by tony on 2017/3/23.
//  本书网站：http://www.51work6.com
//  智捷课堂在线课堂：http://www.zhijieketang.com/
//  智捷课堂微信公共号：zhijieketang
//  作者微博：@tony_关东升
//  作者微信：tony关东升
//  QQ：569418560 邮箱：eorient@sina.com
//  QQ交流群：162030268
//

import UIKit

class MyView: UIView {

    override func draw(_ rect: CGRect) {
        UIColor.black.setFill()
        UIRectFill(rect)
        
        UIColor.white.setStroke()
        let frame = CGRect(x: 20, y: 30, width: 100, height: 300)
        UIRectFrame(frame)
    }

}
