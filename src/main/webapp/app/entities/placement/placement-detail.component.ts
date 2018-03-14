import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Placement } from './placement.model';
import { PlacementService } from './placement.service';

@Component({
    selector: 'jhi-placement-detail',
    templateUrl: './placement-detail.component.html'
})
export class PlacementDetailComponent implements OnInit, OnDestroy {

    placement: Placement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private placementService: PlacementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlacements();
    }

    load(id) {
        this.placementService.find(id)
            .subscribe((placementResponse: HttpResponse<Placement>) => {
                this.placement = placementResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlacements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'placementListModification',
            (response) => this.load(this.placement.id)
        );
    }
}
