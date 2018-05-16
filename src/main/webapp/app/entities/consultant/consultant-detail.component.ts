import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs/Subscription';

import { HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Placement } from '../placement/placement.model';
import { Consultant } from './consultant.model';
import { ConsultantService } from './consultant.service';
import { PlacementService } from '../placement/placement.service';

@Component({
    selector: 'jhi-consultant-detail',
    templateUrl: './consultant-detail.component.html'
})
export class ConsultantDetailComponent implements OnInit, OnDestroy {

    consultant: Consultant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    consultantPlacements: Placement[];

    constructor(
        private eventManager: JhiEventManager,
        private consultantService: ConsultantService,
        private placementService: PlacementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.loadConsultantPlacements(params['id']);
        });
        this.registerChangeInConsultants();
    }

    load(id) {
        this.consultantService.find(id)
            .subscribe((consultantResponse: HttpResponse<Consultant>) => {
                this.consultant = consultantResponse.body;
            });
    }

    loadConsultantPlacements(id) {
        this.placementService.findConsultantPlacements(id)
            .subscribe((consultantPlacementsResponse: HttpResponse<Placement[]>) => {
                this.consultantPlacements = consultantPlacementsResponse.body;
            });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConsultants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'consultantListModification',
            (response) => this.load(this.consultant.id)
        );
    }
}
