import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ConventionComponent } from './convention.component';
import { ConventionDetailComponent } from './convention-detail.component';
import { ConventionPopupComponent } from './convention-dialog.component';
import { ConventionDeletePopupComponent } from './convention-delete-dialog.component';

@Injectable()
export class ConventionResolvePagingParams implements Resolve<any> {

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

export const conventionRoute: Routes = [
    {
        path: 'convention',
        component: ConventionComponent,
        resolve: {
            'pagingParams': ConventionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.convention.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'convention/:id',
        component: ConventionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.convention.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const conventionPopupRoute: Routes = [
    {
        path: 'convention-new',
        component: ConventionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.convention.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'convention/:id/edit',
        component: ConventionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.convention.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'convention/:id/delete',
        component: ConventionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.convention.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
