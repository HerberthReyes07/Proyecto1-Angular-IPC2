import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ControlPoint } from 'src/app/model/control-point';
import { Route } from 'src/app/model/route';
import { User } from 'src/app/model/user';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-control-point-management',
  templateUrl: './control-point-management.component.html',
  styleUrls: ['./control-point-management.component.css']
})
export class ControlPointManagementComponent implements OnInit {

  controlPoints!: ControlPoint[];
  controlPointsFiltered!: ControlPoint[];
  routes!: Route[];
  operators!: User[];
  limit!: number;

  isControlPointFormOpened = false;
  controlPointToUpdate!: ControlPoint;
  isNewControlPoint = true;
  message = "";
  //canProceed = false;

  filterForm = new FormGroup({
    orderNo: new FormControl('0'),
    route: new FormControl('0'),
    operationFee: new FormControl('0'),
    queueCapacity: new FormControl('0'),
  });

  ngOnInit(): void {
    this.recepService.getAllRoutes().subscribe(dataRoutes => {
      if (dataRoutes) {
        this.routes = dataRoutes;
        this.adminService.getAllOperatorsUsers().subscribe(dataOperators => {
          if (dataOperators) {
            this.operators = dataOperators;
            this.adminService.getAllControlPoints().subscribe(dataControlPoints => {
              if (dataControlPoints) {
                this.controlPoints = dataControlPoints.sort(this.orderByOrderNo).sort(this.orderByRouteId);
                this.controlPointsFiltered = dataControlPoints.sort(this.orderByOrderNo).sort(this.orderByRouteId);
                this.limit = dataControlPoints.reduce((maxOrderNo, cp) => Math.max(maxOrderNo, cp.orderNo), dataControlPoints[0].orderNo);
              }
            });
          }
        });
      }
    });
  }

  constructor(private adminService: AdminService, private recepService: RecepService) {
    this.controlPointsFiltered = new Array();
  }

  filter() {
    //this.controlPointsFiltered = this.controlPoints.sort(this.orderByOrderNo).sort(this.orderByRouteId);
    if (this.ordenNoControl.value === "0") {
      if (this.routeControl.value === "0") {
        if (this.queueCapacityControl.value === "0") {
          if (this.operationFeeControl.value == "0") {
            this.controlPointsFiltered = this.controlPoints;
          } else {
            if (this.operationFeeControl.value === "1") {
              this.controlPointsFiltered = [... this.controlPoints].sort(this.compareOperationFeeDesc);
            } else {
              this.controlPointsFiltered = [... this.controlPoints].sort(this.compareOperationFeeAsc);
            }
          }
        } else {
          if (this.queueCapacityControl.value === "1") {
            this.controlPointsFiltered = [... this.controlPoints].sort(this.compareQueueCapacityDesc);
          } else {
            this.controlPointsFiltered = [... this.controlPoints].sort(this.compareQueueCapacityAsc);
          }
        }
      } else {
        this.controlPointsFiltered = this.controlPoints.filter(item => item.routeId === parseInt(this.routeControl.value));
        this.orderBy();
      }
    } else {
      if (this.routeControl.value === "0") {
        this.controlPointsFiltered = this.controlPoints.filter(item => item.orderNo === parseInt(this.ordenNoControl.value));
        this.orderBy();
      } else {
        this.controlPointsFiltered = this.controlPoints.filter(item => (item.routeId === parseInt(this.routeControl.value) && item.orderNo === parseInt(this.ordenNoControl.value)));
      }
    }
  }

  newControlPoint() { 
    this.isNewControlPoint = true;
    this.toggleControlPointFormVisibility();
  }

  updateControlPoint(controlPoint: ControlPoint){
    this.isNewControlPoint = false;
    this.controlPointToUpdate = controlPoint;
    this.toggleControlPointFormVisibility();
  }

  actionCompleted(){
    this.adminService.getAllControlPoints().subscribe(dataControlPoints => {
      if (dataControlPoints) {
        this.controlPoints = dataControlPoints.sort(this.orderByOrderNo).sort(this.orderByRouteId);
        this.limit = dataControlPoints.reduce((maxOrderNo, cp) => Math.max(maxOrderNo, cp.orderNo), dataControlPoints[0].orderNo);
        this.filter();
      }
    });
  }

  get ordenNoControl(): FormControl {
    return this.filterForm.get('orderNo') as FormControl;
  }

  get routeControl(): FormControl {
    return this.filterForm.get('route') as FormControl;
  }

  get queueCapacityControl(): FormControl {
    return this.filterForm.get('queueCapacity') as FormControl;
  }

  get operationFeeControl(): FormControl {
    return this.filterForm.get('operationFee') as FormControl;
  }

  getRouteById(routeId: number) {
    for (let index = 0; index < this.routes.length; index++) {
      if (this.routes[index].id === routeId) {
        return this.routes[index];
      }
    }
    return this.routes[0];
  }

  getOperatorById(operatorId: number) {
    for (let index = 0; index < this.operators.length; index++) {
      if (this.operators[index].id === operatorId) {
        return this.operators[index]
      }
    }
    return this.operators[0];
  }

  orderByOrderNo(a: ControlPoint, b: ControlPoint){
    return a.orderNo - b.orderNo;
  }

  orderByRouteId(a: ControlPoint, b: ControlPoint){
    return a.routeId - b.routeId;
  }

  compareQueueCapacityAsc(a: ControlPoint, b: ControlPoint) {
    return a.queueCapacity - b.queueCapacity;
  }

  compareQueueCapacityDesc(a: ControlPoint, b: ControlPoint) {
    return b.queueCapacity - a.queueCapacity;
  }

  compareOperationFeeAsc(a: ControlPoint, b: ControlPoint) {
    return a.localOperationFee - b.localOperationFee;
  }

  compareOperationFeeDesc(a: ControlPoint, b: ControlPoint) {
    return b.localOperationFee - a.localOperationFee;
  }

  orderBy() {
    if (this.queueCapacityControl.value !== "0") {
      if (this.operationFeeControl.value !== "0") {
        if (this.queueCapacityControl.value !== "0") {
          this.queueCapacityControl.setValue('0');
        }
        if (this.operationFeeControl.value === "1") {
          this.controlPointsFiltered = [... this.controlPointsFiltered].sort(this.compareOperationFeeDesc);
        } else {
          this.controlPointsFiltered = [... this.controlPointsFiltered].sort(this.compareOperationFeeAsc);
        }
      } else {
        if (this.queueCapacityControl.value === "1") {
          this.controlPointsFiltered = [... this.controlPointsFiltered].sort(this.compareQueueCapacityDesc);
        } else {
          this.controlPointsFiltered = [... this.controlPointsFiltered].sort(this.compareQueueCapacityAsc);
        }
      }
    } else {
      if (this.operationFeeControl.value !== "0") {
        if (this.operationFeeControl.value === "1") {
          this.controlPointsFiltered = [... this.controlPointsFiltered].sort(this.compareOperationFeeDesc);
        } else {
          this.controlPointsFiltered = [... this.controlPointsFiltered].sort(this.compareOperationFeeAsc);
        }
      }
    }
  }

  toggleControlPointFormVisibility() {
    this.isControlPointFormOpened = !this.isControlPointFormOpened;
  }
}
