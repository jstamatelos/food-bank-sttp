import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { HomePage } from '../home/home';
import { FoodCategory } from '../foodcategory/foodcategory';

@Component({
  selector: 'page-partnerprofile',
  templateUrl: 'partnerprofile.html'
})
export class PartnerProfile {

  constructor(public navCtrl: NavController) {

  }
  goToMenu() {
    this.navCtrl.setRoot(HomePage);
  }

  goToOrderForm(){
    this.navCtrl.push(FoodCategory);
  }

}
