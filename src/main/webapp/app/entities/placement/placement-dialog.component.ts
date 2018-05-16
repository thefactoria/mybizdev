import { IMultiSelectOption, IMultiSelectTexts, IMultiSelectSettings } from 'angular-2-dropdown-multiselect';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { Observable } from 'rxjs/Observable';

import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { BizDev, BizDevService } from '../biz-dev';
import { Consultant, ConsultantService } from '../consultant';
import { PlacementPopupService } from './placement-popup.service';
import { Placement, Statut } from './placement.model';
import { PlacementService } from './placement.service';

@Component({
    selector: 'jhi-placement-dialog',
    templateUrl: './placement-dialog.component.html',
})
export class PlacementDialogComponent implements OnInit {

    placement: Placement;
    placements: Placement[];
    isSaving: boolean;
    get nomClientFinalOrnomSSIIError(): boolean {
        return !this.placement.nomClientFinal && !this.placement.nomSSII;
    }

    consultants: Consultant[];

    placementConsultants: number[] = [];

    // Settings configuration
    palcementConsultantsSettings: IMultiSelectSettings = {
        enableSearch: true,
        checkedStyle: 'fontawesome',
        buttonClasses: 'form-control',
        dynamicTitleMaxItems: 3,
        displayAllSelectedText: true
    };
    palcementConsultantsTexts: IMultiSelectTexts = {
        checkAll: 'Select all',
        uncheckAll: 'Unselect all',
        checked: 'item selected',
        checkedPlural: 'items selected',
        searchPlaceholder: 'Find',
        searchEmptyResult: 'Nothing found...',
        searchNoRenderText: 'Type in search box to see results...',
        defaultTitle: 'Select one or multiple consultants',
        allSelected: 'All selected',
    };
    consultantOptions: IMultiSelectOption[] = [];

    bizdevs: BizDev[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private placementService: PlacementService,
        private consultantService: ConsultantService,
        private bizDevService: BizDevService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.consultantService.query()
            .subscribe(
                (res: HttpResponse<Consultant[]>) => {
                    this.consultants = res.body;
                    res.body.forEach((c: Consultant) => {
                        this.consultantOptions.push({ id: c.id, name: c.nom + ' ' + c.prenom, isLabel: false });
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.bizDevService.query()
            .subscribe((res: HttpResponse<BizDev[]>) => { this.bizdevs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    onGoInMission(placement: Placement, tjmFinal: number) {
        this.isSaving = true;
        this.subscribeToSaveResponse(this.placementService.goInMission(this.placement, tjmFinal));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        if (this.nomClientFinalOrnomSSIIError) {
            return;
        }
        this.isSaving = true;
        if (this.placement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.placementService.update(this.placement));
        } else {
            this.placements = [];
            this.placementConsultants.forEach((consultantId: number) => {
                const newPlacement: Placement = Object.assign({}, this.placement, { consultant: { id: consultantId } });
                this.placements.push(newPlacement);
            });
            this.subscribeToSaveArrayResponse(this.placementService.createAll(this.placements));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Placement>>) {
        result.subscribe((res: HttpResponse<Placement>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private subscribeToSaveArrayResponse(result: Observable<HttpResponse<Placement[]>>) {
        result.subscribe(
            (res: HttpResponse<Placement[]>) => this.onSaveAllSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: Placement) {
        this.eventManager.broadcast({ name: 'placementListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveAllSuccess(result: Placement[]) {
        this.eventManager.broadcast({ name: 'placementListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackConsultantById(index: number, item: Consultant) {
        return item.id;
    }

    trackBizDevById(index: number, item: BizDev) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-placement-popup',
    template: ''
})
export class PlacementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private placementPopupService: PlacementPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.placementPopupService
                    .open(PlacementDialogComponent as Component, params['id']);
            } else {
                this.placementPopupService
                    .open(PlacementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
