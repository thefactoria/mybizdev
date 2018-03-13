import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Etat } from './etat.model';
import { EtatPopupService } from './etat-popup.service';
import { EtatService } from './etat.service';
import { Placement, PlacementService } from '../placement';

@Component({
    selector: 'jhi-etat-dialog',
    templateUrl: './etat-dialog.component.html'
})
export class EtatDialogComponent implements OnInit {

    etat: Etat;
    isSaving: boolean;

    placements: Placement[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private etatService: EtatService,
        private placementService: PlacementService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.placementService
            .query({filter: 'etat-is-null'})
            .subscribe((res: HttpResponse<Placement[]>) => {
                if (!this.etat.placement || !this.etat.placement.id) {
                    this.placements = res.body;
                } else {
                    this.placementService
                        .find(this.etat.placement.id)
                        .subscribe((subRes: HttpResponse<Placement>) => {
                            this.placements = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.etat.id !== undefined) {
            this.subscribeToSaveResponse(
                this.etatService.update(this.etat));
        } else {
            this.subscribeToSaveResponse(
                this.etatService.create(this.etat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Etat>>) {
        result.subscribe((res: HttpResponse<Etat>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Etat) {
        this.eventManager.broadcast({ name: 'etatListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPlacementById(index: number, item: Placement) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-etat-popup',
    template: ''
})
export class EtatPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private etatPopupService: EtatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.etatPopupService
                    .open(EtatDialogComponent as Component, params['id']);
            } else {
                this.etatPopupService
                    .open(EtatDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
