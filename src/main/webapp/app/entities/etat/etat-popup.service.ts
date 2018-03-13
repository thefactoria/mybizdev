import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Etat } from './etat.model';
import { EtatService } from './etat.service';

@Injectable()
export class EtatPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private etatService: EtatService

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
                this.etatService.find(id)
                    .subscribe((etatResponse: HttpResponse<Etat>) => {
                        const etat: Etat = etatResponse.body;
                        etat.dateDemarrage = this.datePipe
                            .transform(etat.dateDemarrage, 'yyyy-MM-ddTHH:mm:ss');
                        etat.dateDebutInterco = this.datePipe
                            .transform(etat.dateDebutInterco, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.etatModalRef(component, etat);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.etatModalRef(component, new Etat());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    etatModalRef(component: Component, etat: Etat): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.etat = etat;
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
