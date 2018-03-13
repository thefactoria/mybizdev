import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BizDev } from './biz-dev.model';
import { BizDevService } from './biz-dev.service';

@Component({
    selector: 'jhi-biz-dev-detail',
    templateUrl: './biz-dev-detail.component.html'
})
export class BizDevDetailComponent implements OnInit, OnDestroy {

    bizDev: BizDev;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bizDevService: BizDevService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBizDevs();
    }

    load(id) {
        this.bizDevService.find(id)
            .subscribe((bizDevResponse: HttpResponse<BizDev>) => {
                this.bizDev = bizDevResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBizDevs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bizDevListModification',
            (response) => this.load(this.bizDev.id)
        );
    }
}
