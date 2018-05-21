import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { Observable } from 'rxjs/Observable';
import { debounceTime, distinctUntilChanged, filter, mergeMap } from 'rxjs/operators';
import { Subject } from 'rxjs/Subject';
import { Subscription } from 'rxjs/Subscription';

import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { Placement } from './placement.model';
import { PlacementService } from './placement.service';

@Component({
    selector: 'jhi-placement',
    templateUrl: './placement.component.html'
})
export class PlacementComponent implements OnInit, OnDestroy {

    placements: Placement[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    keyword: string;
    filteredPlacements: Placement[];
    showArchivedPlacements = false;

    constructor(
        private placementService: PlacementService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.placements = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.placementService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: HttpResponse<Placement[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    reset() {
        this.page = 0;
        this.placements = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlacements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Placement) {
        return item.id;
    }
    registerChangeInPlacements() {
        this.eventSubscriber = this.eventManager.subscribe('placementListModification', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    onSearch(keyword?: string) {
        this.keyword = keyword;
        this.filteredPlacements = Object.assign([], this.placements);
        this.filterPlacements(keyword.toLowerCase());
        this.filterArchivedPlacements();
    }

    onShowArchivedPlacements(showArchived: boolean) {
        this.showArchivedPlacements = showArchived;
        this.filteredPlacements = Object.assign([], this.placements);
        if (!!this.keyword) {
            this.filterPlacements(this.keyword);
        }
        this.filterArchivedPlacements();
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.placements.push(data[i]);
        }
        this.filteredPlacements = Object.assign([], this.placements);
        this.filterArchivedPlacements();
    }

    private filterPlacements(keyword: string) {
        this.filteredPlacements = this.filteredPlacements.filter((p: Placement) => {
            return (p.nomClientFinal || '').toLowerCase().indexOf(keyword) > -1 ||
                (p.nomSSII || '').toLowerCase().indexOf(keyword) > -1 ||
                (p.consultant.nom || '').toLowerCase().indexOf(keyword) > -1 ||
                (p.bizDev.surnom || '').toLowerCase().indexOf(keyword) > -1;
        });
    }

    private filterArchivedPlacements() {
        this.filteredPlacements = (this.showArchivedPlacements) ? this.filteredPlacements : this.filteredPlacements.filter((p: Placement) => !p.archived);
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
