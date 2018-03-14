import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ConsultantComponent } from './consultant.component';
import { ConsultantDetailComponent } from './consultant-detail.component';
import { ConsultantPopupComponent } from './consultant-dialog.component';
import { ConsultantDeletePopupComponent } from './consultant-delete-dialog.component';

@Injectable()
export class ConsultantResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const consultantRoute: Routes = [
    {
        path: 'consultant',
        component: ConsultantComponent,
        resolve: {
            'pagingParams': ConsultantResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultants'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'consultant/:id',
        component: ConsultantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const consultantPopupRoute: Routes = [
    {
        path: 'consultant-new',
        component: ConsultantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultant/:id/edit',
        component: ConsultantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultant/:id/delete',
        component: ConsultantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
