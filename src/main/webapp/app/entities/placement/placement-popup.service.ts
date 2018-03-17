import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Placement } from './placement.model';
import { PlacementService } from './placement.service';

@Injectable()
export class PlacementPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private placementService: PlacementService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.placementService.find(id)
                    .subscribe((placementResponse: HttpResponse<Placement>) => {
                        const placement: Placement = placementResponse.body;
                        if (placement.dateDemarrage) {
                            placement.dateDemarrage = {
                                year: placement.dateDemarrage.getFullYear(),
                                month: placement.dateDemarrage.getMonth() + 1,
                                day: placement.dateDemarrage.getDate()
                            };
                        }
                        this.ngbModalRef = this.placementModalRef(component, placement);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.placementModalRef(component, new Placement());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    placementModalRef(component: Component, placement: Placement): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.placement = placement;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
