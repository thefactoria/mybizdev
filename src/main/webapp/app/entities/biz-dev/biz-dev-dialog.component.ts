import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BizDev } from './biz-dev.model';
import { BizDevPopupService } from './biz-dev-popup.service';
import { BizDevService } from './biz-dev.service';
import { Placement, PlacementService } from '../placement';
import { Equipe, EquipeService } from '../equipe';

@Component({
    selector: 'jhi-biz-dev-dialog',
    templateUrl: './biz-dev-dialog.component.html'
})
export class BizDevDialogComponent implements OnInit {

    bizDev: BizDev;
    isSaving: boolean;

    placements: Placement[];

    equipes: Equipe[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private bizDevService: BizDevService,
        private placementService: PlacementService,
        private equipeService: EquipeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.placementService.query()
            .subscribe((res: HttpResponse<Placement[]>) => { this.placements = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.equipeService.query()
            .subscribe((res: HttpResponse<Equipe[]>) => { this.equipes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bizDev.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bizDevService.update(this.bizDev));
        } else {
            this.subscribeToSaveResponse(
                this.bizDevService.create(this.bizDev));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BizDev>>) {
        result.subscribe((res: HttpResponse<BizDev>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BizDev) {
        this.eventManager.broadcast({ name: 'bizDevListModification', content: 'OK'});
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

    trackEquipeById(index: number, item: Equipe) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-biz-dev-popup',
    template: ''
})
export class BizDevPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bizDevPopupService: BizDevPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bizDevPopupService
                    .open(BizDevDialogComponent as Component, params['id']);
            } else {
                this.bizDevPopupService
                    .open(BizDevDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
