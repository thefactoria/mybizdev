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

import * as _ from 'lodash';

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

    filterHeaderOn = false;
    filter: any = {};
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

    onResetFilters() {
        this.filter = {
            etats: {
                all: true
            }
        };
        this.filteredPlacements = Object.assign([], this.placements);
        this.filterPlacementsByKeywords();
        this.filterArchivedPlacements();
    }

    toggleFilterHeader() {
        this.filterHeaderOn = !this.filterHeaderOn;
        this.onResetFilters();
    }

    onShowArchivedPlacements(showArchived: boolean) {
        this.showArchivedPlacements = showArchived;
        this.onFilter();
    }

    onAllEtatChecked(checked: boolean) {
        this.filter.etats = (checked) ? { all: true } : this.filter.etats;
        this.onFilter();
    }

    onEtatChecked() {
        this.filter.etats['all'] = false;
        this.onFilter();
    }

    onFilter() {
        this.filteredPlacements = Object.assign([], this.placements);
        this.filterPlacementsByEtat();
        this.filterArchivedPlacements();
        this.filterPlacementsByKeywords();
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.placements.push(data[i]);
        }
        this.filteredPlacements = Object.assign([], this.placements);
        if (this.filterHeaderOn) {
            this.filterPlacementsByEtat();
        }
        this.filterArchivedPlacements();
        if (this.filterHeaderOn) {
            this.filterPlacementsByKeywords();
        }
    }

    private filterPlacementsByKeywords() {
        this.filteredPlacements = this.filteredPlacements.filter((p: Placement) => {
            return (p.nomClientFinal || '').toLowerCase().indexOf((this.filter.nomClientFinal || '').toLowerCase()) > -1 &&
                (p.nomSSII || '').toLowerCase().indexOf((this.filter.nomSSII || '').toLowerCase()) > -1 &&
                (p.consultant.nom || '').toLowerCase().indexOf((this.filter.consultant || '').toLowerCase()) > -1 &&
                (p.bizDev.surnom || '').toLowerCase().indexOf((this.filter.bizDev || '').toLowerCase()) > -1;
        });
    }

    private filterPlacementsByEtat() {
        if (!this.filter.etats['all']) {
            this.filteredPlacements = [];
            Object.keys(this.filter.etats).filter((k) => k !== 'all' && this.filter.etats[k])
                .forEach((k) => {
                    const filtered = this.placements.filter((p: Placement) => p.etat.toString().toLowerCase() === k.toLocaleLowerCase()) || [];
                    if (this.filteredPlacements.length === 0) {
                        this.filteredPlacements = (filtered.length > 0) ? filtered : [];
                    } else if (this.filteredPlacements.length > 0) {
                        this.filteredPlacements = [...this.filteredPlacements, ...filtered];
                    }
                });
        }
    }

    private filterArchivedPlacements() {
        this.filteredPlacements = (this.showArchivedPlacements) ? this.filteredPlacements : this.filteredPlacements.filter((p: Placement) => !p.archived);
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
