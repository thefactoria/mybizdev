import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BizDev } from './biz-dev.model';
import { BizDevService } from './biz-dev.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-biz-dev',
    templateUrl: './biz-dev.component.html'
})
export class BizDevComponent implements OnInit, OnDestroy {
bizDevs: BizDev[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bizDevService: BizDevService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bizDevService.query().subscribe(
            (res: HttpResponse<BizDev[]>) => {
                this.bizDevs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBizDevs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BizDev) {
        return item.id;
    }
    registerChangeInBizDevs() {
        this.eventSubscriber = this.eventManager.subscribe('bizDevListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
